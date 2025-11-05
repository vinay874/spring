package com.agent;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NaukriAgent {

    private static final String NAUKRI_EMAIL = System.getenv("NAUKRI_EMAIL");
    private static final String NAUKRI_PASS  = System.getenv("NAUKRI_PASS");
    private static final String RESUME_PATH  = System.getenv("RESUME_PATH");
    private static final String KEYWORD      = System.getenv().getOrDefault("KEYWORD", "Spring Boot Developer");
    private static final String LOCATION     = System.getenv().getOrDefault("LOCATION", "Hyderabad");
    private static final int MAX_APPLY_PER_RUN = Integer.parseInt(System.getenv().getOrDefault("MAX_APPLY", "3"));
    private static final ApplicationTracker tracker = new ApplicationTracker();

    private WebDriver driver;
    private WebDriverWait wait;
    private Connection db;

    public static void main(String[] args) {
        NaukriAgent agent = null;
        try {
            agent = new NaukriAgent();
            agent.setup();
            agent.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (agent != null) {
                try { agent.shutdown(); } catch (Exception ignored) {}
            }
        }
    }

    public void setup() throws Exception {
        if (NAUKRI_EMAIL == null || NAUKRI_PASS == null || RESUME_PATH == null) {
            throw new IllegalStateException("Set NAUKRI_EMAIL, NAUKRI_PASS, and RESUME_PATH environment variables first.");
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
//        options.addArguments("--headless=new");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        db = DriverManager.getConnection("jdbc:sqlite:naukri_agent.db");
        try (Statement s = db.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS applied (" +
                    "job_id TEXT PRIMARY KEY, job_url TEXT, title TEXT, company TEXT, applied_at TEXT, status TEXT)");
        }
    }

    public void run() throws Exception {
        String[] keywords = KEYWORD.split(",");
        int totalApplied = 0;
        login();
        for (String raw : keywords) {
            String keyword = raw.trim();
            if (keyword.isEmpty()) continue;

            System.out.println("\n=============================");
            System.out.println("🎯 Searching for keyword: " + keyword);
            System.out.println("=============================\n");

            searchJobs(keyword, LOCATION);
            List<String> links = collectJobLinksOnPage();
            int applied = 0;

            for (String jobUrl : links) {
                if (applied >= MAX_APPLY_PER_RUN) break;
                if (tracker.isAlreadyApplied(jobUrl)) continue;

                if (applyToJob(jobUrl)) {
                    tracker.recordApplication(jobUrl, keyword, "unknown-company", "applied");
                    applied++;
                    totalApplied++;
                }
                Thread.sleep(8000 + new Random().nextInt(5000));
            }

            System.out.println("✅ Finished keyword: " + keyword + " | Applied: " + applied + " jobs.");
        }

        System.out.println("🏁 Total jobs applied this run: " + totalApplied);
    }


    private void login() throws InterruptedException {
        driver.manage().deleteAllCookies();
        driver.get("https://www.naukri.com/nlogin/login");
        Thread.sleep(3000);

        try {
            // handle popup login modal or iframe
            driver.switchTo().defaultContent();
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            if (!iframes.isEmpty()) {
                for (WebElement f : iframes) {
                    driver.switchTo().frame(f);
                    if (driver.findElements(By.xpath("//input[contains(@placeholder,'Email') or @id='usernameField']")).size() > 0) {
                        System.out.println("🪟 Switched to iframe for login");
                        break;
                    } else {
                        driver.switchTo().defaultContent();
                    }
                }
            }

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'Email') or @id='usernameField' or @name='email']")));
            emailField.clear();
            emailField.sendKeys(NAUKRI_EMAIL);
            System.out.println("✍️  Entered email");

            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'password') or @id='passwordField' or @name='password']")));
            passwordField.clear();
            passwordField.sendKeys(NAUKRI_PASS);
            System.out.println("🔑  Entered password");

            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Login') or contains(.,'Sign in') or @type='submit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
            loginBtn.click();
            System.out.println("🚀 Clicked login button");

            Thread.sleep(6000);
            driver.switchTo().defaultContent();
            System.out.println("✅ Logged in successfully or redirected.");

        } catch (TimeoutException e) {
            takeScreenshot("login_timeout.png");
            System.out.println("⚠️ Login timeout. Check login_timeout.png");
            throw e;
        } catch (Exception e) {
            takeScreenshot("login_failed.png");
            System.out.println("❌ Login failed: " + e.getMessage());
            throw e;
        }
    }

    private void searchJobs(String keyword, String location) throws InterruptedException {
        try {
            // go directly to Naukri's job search page
            String searchUrl = "https://www.naukri.com/" +
                    "jobs-in-" + location.toLowerCase().replace(" ", "-") +
                    "?k=" + keyword.replace(" ", "+");
            driver.get(searchUrl);
            System.out.println("🌐 Navigated directly to job search page: " + searchUrl);

            // wait for results
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//article[contains(@class,'jobTuple')]")));
            Thread.sleep(3000);
            System.out.println("✅ Job listings page loaded successfully.");
        } catch (TimeoutException te) {
            System.out.println("⚠️ Could not load job listings. Check if redirected to dashboard.");
            takeScreenshot("search_error.png");
        } catch (Exception e) {
            System.out.println("❌ Unexpected error in search: " + e.getMessage());
            takeScreenshot("search_error.png");
        }
    }


    private List<String> collectJobLinksOnPage() {
        List<String> links = new ArrayList<>();
        try {
            List<WebElement> elems = driver.findElements(By.xpath("//a[contains(@class,'title') and @href]"));
            for (WebElement e : elems) {
                String href = e.getAttribute("href");
                if (href != null && href.startsWith("https://www.naukri.com")) links.add(href);
            }
        } catch (Exception ignored) {}
        System.out.println("📋 Found " + links.size() + " job links.");
        return links;
    }

    private boolean isAlreadyApplied(String url) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("SELECT 1 FROM applied WHERE job_id = ?")) {
            ps.setString(1, url);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void recordApplication(String url, String title, String company, String status) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement(
                "INSERT OR IGNORE INTO applied(job_id, job_url, title, company, applied_at, status) VALUES(?,?,?,?,?,?)")) {
            ps.setString(1, url);
            ps.setString(2, url);
            ps.setString(3, title);
            ps.setString(4, company);
            ps.setString(5, Instant.now().toString());
            ps.setString(6, status);
            ps.executeUpdate();
        }
    }

    private boolean applyToJob(String jobUrl) {
        System.out.println("🧾 Opening: " + jobUrl);
        try {
            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", jobUrl);
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            Thread.sleep(3000);

            // 🔧 Step 1: Close annoying overlays or popups
            try {
                List<By> popupSelectors = Arrays.asList(
                        By.xpath("//button[contains(text(),'Got it')]"),
                        By.xpath("//span[contains(text(),'Later')]"),
                        By.xpath("//div[contains(@class,'crossIcon')]"),
                        By.xpath("//button[contains(@class,'close')]"),
                        By.xpath("//a[contains(@class,'close')]")
                );
                for (By selector : popupSelectors) {
                    List<WebElement> popups = driver.findElements(selector);
                    if (!popups.isEmpty()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popups.get(0));
                        System.out.println("🧹 Closed popup/overlay.");
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception ignored) {}

            // 🔍 Step 2: Find and click Apply button safely
            WebElement applyBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(text(),'Apply') or contains(text(),'Apply Now') or contains(@class,'apply')]")
            ));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", applyBtn);
            Thread.sleep(1000);

            // Try direct JS click (bypasses intercept issues)
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
                System.out.println("✅ Clicked Apply button via JS.");
            } catch (Exception e1) {
                System.out.println("⚠️ JS click failed, retrying native click...");
                try {
                    applyBtn.click();
                    System.out.println("✅ Clicked Apply button natively.");
                } catch (Exception e2) {
                    System.out.println("❌ Could not click Apply: " + e2.getMessage());
                    takeScreenshot("apply_click_error.png");
                    driver.close();
                    driver.switchTo().window(tabs.get(0));
                    return false;
                }
            }

            Thread.sleep(4000);

            // 📎 Step 3: Handle resume upload if required
            List<WebElement> fileInputs = driver.findElements(By.xpath("//input[@type='file']"));
            if (!fileInputs.isEmpty()) {
                fileInputs.get(0).sendKeys(Path.of(RESUME_PATH).toAbsolutePath().toString());
                System.out.println("📎 Uploaded resume.");
                Thread.sleep(2000);
            }

            System.out.println("✅ Application submitted or initiated.");

            // 🧠 Step 4: Handle extra screening questions if present
            try {
                Thread.sleep(3000); // wait for popup to load

                List<WebElement> textFields = driver.findElements(By.xpath("//input[@type='text' or @type='number' or @type='email']"));
                List<WebElement> textAreas = driver.findElements(By.xpath("//textarea"));
                List<WebElement> radioButtons = driver.findElements(By.xpath("//input[@type='radio']"));

                if (!textFields.isEmpty() || !textAreas.isEmpty() || !radioButtons.isEmpty()) {
                    System.out.println("🧩 Detected screening questions. Filling automatically...");

                    // --- Fill text fields with smart defaults ---
                    for (WebElement field : textFields) {
                        String name = field.getAttribute("name");
                        String placeholder = field.getAttribute("placeholder");

                        if (placeholder != null && placeholder.toLowerCase().contains("notice")) {
                            field.sendKeys("15 days");
                        } else if (placeholder != null && placeholder.toLowerCase().contains("ctc")) {
                            field.sendKeys("8 LPA");
                        } else if (placeholder != null && placeholder.toLowerCase().contains("experience")) {
                            field.sendKeys("3 years");
                        } else if (placeholder != null && placeholder.toLowerCase().contains("location")) {
                            field.sendKeys("Bangalore");
                        } else {
                            field.sendKeys("Yes");
                        }
                    }

                    // --- Fill textareas if any ---
                    for (WebElement ta : textAreas) {
                        ta.sendKeys("I am interested in this opportunity and my experience aligns with the role.");
                    }

                    // --- Click first available radio option ---
                    if (!radioButtons.isEmpty()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioButtons.get(0));
                    }

                    Thread.sleep(1000);

                    // --- Click submit or save ---
                    List<WebElement> submitButtons = driver.findElements(By.xpath(
                            "//button[contains(text(),'Submit') or contains(text(),'Apply') or contains(text(),'Save')]"
                    ));
                    if (!submitButtons.isEmpty()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButtons.get(0));
                        System.out.println("📨 Submitted screening form successfully.");
                    } else {
                        System.out.println("⚠️ No Submit button found.");
                    }

                    Thread.sleep(2000);
                } else {
                    System.out.println("ℹ️ No extra questions found. Continuing...");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error filling screening form: " + e.getMessage());
            }


            driver.close();
            driver.switchTo().window(tabs.get(0));
            return true;
        } catch (Exception ex) {
            System.out.println("❌ Error applying to job: " + ex.getMessage());
            takeScreenshot("apply_error.png");
            try { driver.close(); } catch (Exception ignored) {}
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
            return false;
        }
    }


    private void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            java.nio.file.Files.write(java.nio.file.Paths.get(name), src);
            System.out.println("📸 Screenshot saved: " + name);
        } catch (Exception ignored) {}
    }

    public void shutdown() throws Exception {
        if (driver != null) driver.quit();
        if (db != null) db.close();
    }
}

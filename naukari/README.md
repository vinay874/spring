# Naukri Agent (Java + Selenium + SQLite + JavaMail)

### 🚀 Overview
This project automates Naukri job applications, tracks applied jobs using SQLite, and includes email send/read utilities.

---

### 🧰 Requirements
- Java 17+
- Maven
- Chrome installed
- ChromeDriver (auto-managed by WebDriverManager)

---

### ⚙️ Environment Variables
| Variable | Description |
|-----------|--------------|
| `NAUKRI_EMAIL` | Your Naukri login email |
| `NAUKRI_PASS` | Your Naukri password |
| `RESUME_PATH` | Absolute path to your resume PDF |
| `KEYWORD` | Job keyword (e.g., Spring Boot Developer) |
| `LOCATION` | Job location (e.g., Hyderabad) |
| `MAX_APPLY` | How many jobs to apply per run |
| `SMTP_*` / `IMAP_*` | Email credentials for send/receive |

---

### 🧪 Run Locally
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.agent.NaukriAgent"

package com.agent;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ApplicationTracker {
    private static final String DB_URL = "jdbc:sqlite:applied_jobs.db";
    private Connection connection;

    public ApplicationTracker() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS applied_jobs (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        job_url TEXT UNIQUE,
                        keyword TEXT,
                        company TEXT,
                        status TEXT,
                        applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlreadyApplied(String jobUrl) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM applied_jobs WHERE job_url = ?")) {
            stmt.setString(1, jobUrl);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void recordApplication(String jobUrl, String keyword, String company, String status) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT OR IGNORE INTO applied_jobs(job_url, keyword, company, status) VALUES(?, ?, ?, ?)")) {
            stmt.setString(1, jobUrl);
            stmt.setString(2, keyword);
            stmt.setString(3, company);
            stmt.setString(4, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getAllAppliedJobs() {
        Set<String> urls = new HashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT job_url FROM applied_jobs")) {
            while (rs.next()) {
                urls.add(rs.getString("job_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urls;
    }
}

package com.femaledaily.qa.listeners;

import com.femaledaily.qa.driver.DriverManager;
import com.femaledaily.qa.reporting.ScreenshotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    private static final Logger log = LoggerFactory.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test started: {}", result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}", result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test failed: {}", result.getName());
        if (DriverManager.getDriver() != null) {
            String screenshotPath = ScreenshotManager.captureScreenshot(result.getName());
            log.info("Screenshot saved: {}", screenshotPath);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test skipped: {}", result.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        log.info("Test suite finished: {}", context.getName());
    }
}

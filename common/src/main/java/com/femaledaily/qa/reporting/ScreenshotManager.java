package com.femaledaily.qa.reporting;

import com.femaledaily.qa.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.femaledaily.qa.constants.FrameworkConstants.SCREENSHOTS_PATH;

public class ScreenshotManager {
    
    private static final Logger log = LoggerFactory.getLogger(ScreenshotManager.class);
    
    public static String captureScreenshot(String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOTS_PATH + fileName;
        
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_PATH));
            File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), Paths.get(filePath));
            log.info("Screenshot captured: {}", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("Failed to capture screenshot", e);
            return null;
        }
    }
    
    private ScreenshotManager() {}
}

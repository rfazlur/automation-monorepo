package com.femaledaily.qa.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.femaledaily.qa.constants.FrameworkConstants.*;

public class DriverManager {
    
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    
    public static RemoteWebDriver getDriver() {
        return driver.get();
    }
    
    public static void setDriver(RemoteWebDriver webDriver) {
        driver.set(webDriver);
    }
    
    public static void initBrowser(String browser) {
        RemoteWebDriver webDriver = switch (browser.toLowerCase()) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        webDriver.manage().window().maximize();
        
        setDriver(webDriver);
        log.info("Initialized {} browser", browser);
    }
    
    private static ChromeDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }
    
    private static FirefoxDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        return new FirefoxDriver(options);
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            log.info("Driver quit successfully");
        }
    }
}

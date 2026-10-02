package com.femaledaily.qa.utils;

import com.femaledaily.qa.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.femaledaily.qa.constants.FrameworkConstants.EXPLICIT_WAIT;

public class WaitUtils {
    
    public static WebElement waitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_WAIT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static WebElement waitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_WAIT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static boolean waitForElementInvisible(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(EXPLICIT_WAIT));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    private WaitUtils() {}
}

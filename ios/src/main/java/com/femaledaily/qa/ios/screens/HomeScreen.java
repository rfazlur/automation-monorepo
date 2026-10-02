package com.femaledaily.qa.ios.screens;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.femaledaily.qa.constants.FrameworkConstants.EXPLICIT_WAIT;

public class HomeScreen {
    
    private final WebDriver driver;
    
    private final By welcomeMessage = AppiumBy.accessibilityId("welcome_message");
    
    public HomeScreen(WebDriver driver) {
        this.driver = driver;
    }
    
    public boolean isWelcomeMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }
    
    public String getWelcomeText() {
        return driver.findElement(welcomeMessage).getText();
    }
}

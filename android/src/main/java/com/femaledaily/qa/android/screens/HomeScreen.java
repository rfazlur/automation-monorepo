package com.femaledaily.qa.android.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomeScreen {
    
    private final AndroidDriver driver;
    
    private final By welcomeMessage = AppiumBy.id("com.femaledaily.app:id/welcome_message");
    private final By userProfile = AppiumBy.id("com.femaledaily.app:id/user_profile");
    
    public HomeScreen(AndroidDriver driver) {
        this.driver = driver;
    }
    
    public boolean isWelcomeMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }
    
    public String getWelcomeText() {
        return driver.findElement(welcomeMessage).getText();
    }
}

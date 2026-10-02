package com.femaledaily.qa.web.pages;

import com.femaledaily.qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    
    private final WebDriver driver;
    
    private final By welcomeMessage = By.className("welcome-message");
    private final By userProfile = By.id("user-profile");
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public boolean isWelcomeMessageDisplayed() {
        return WaitUtils.waitForElementVisible(welcomeMessage).isDisplayed();
    }
    
    public String getWelcomeText() {
        return WaitUtils.waitForElementVisible(welcomeMessage).getText();
    }
}

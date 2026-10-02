package com.femaledaily.qa.web.pages;

import com.femaledaily.qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    
    private final WebDriver driver;
    
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.className("error-message");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public LoginPage enterUsername(String username) {
        WaitUtils.waitForElementVisible(usernameField).sendKeys(username);
        return this;
    }
    
    public LoginPage enterPassword(String password) {
        WaitUtils.waitForElementVisible(passwordField).sendKeys(password);
        return this;
    }
    
    public HomePage clickLogin() {
        WaitUtils.waitForElementClickable(loginButton).click();
        return new HomePage(driver);
    }
    
    public String getErrorMessage() {
        return WaitUtils.waitForElementVisible(errorMessage).getText();
    }
}

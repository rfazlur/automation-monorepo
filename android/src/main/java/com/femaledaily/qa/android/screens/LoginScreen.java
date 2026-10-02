package com.femaledaily.qa.android.screens;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.femaledaily.qa.constants.FrameworkConstants.EXPLICIT_WAIT;

public class LoginScreen {
    
    private final WebDriver driver;
    
    private final By usernameField = AppiumBy.id("com.femaledaily.app:id/username");
    private final By passwordField = AppiumBy.id("com.femaledaily.app:id/password");
    private final By loginButton = AppiumBy.id("com.femaledaily.app:id/login_button");
    private final By errorMessage = AppiumBy.id("com.femaledaily.app:id/error_message");
    
    public LoginScreen(WebDriver driver) {
        this.driver = driver;
    }
    
    public LoginScreen enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        return this;
    }
    
    public LoginScreen enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }
    
    public HomeScreen clickLogin() {
        driver.findElement(loginButton).click();
        return new HomeScreen(driver);
    }
    
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}

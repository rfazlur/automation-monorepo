package com.femaledaily.qa.ios.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.femaledaily.qa.constants.FrameworkConstants.EXPLICIT_WAIT;

public class LoginScreen {
    
    private final IOSDriver driver;
    
    private final By usernameField = AppiumBy.accessibilityId("username_field");
    private final By passwordField = AppiumBy.accessibilityId("password_field");
    private final By loginButton = AppiumBy.accessibilityId("login_button");
    private final By errorMessage = AppiumBy.accessibilityId("error_message");
    
    public LoginScreen(IOSDriver driver) {
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

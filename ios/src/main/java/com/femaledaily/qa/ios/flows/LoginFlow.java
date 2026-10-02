package com.femaledaily.qa.ios.flows;

import com.femaledaily.qa.ios.screens.HomeScreen;
import com.femaledaily.qa.ios.screens.LoginScreen;
import org.openqa.selenium.WebDriver;

public class LoginFlow {
    
    private final WebDriver driver;
    
    public LoginFlow(WebDriver driver) {
        this.driver = driver;
    }
    
    public HomeScreen login(String username, String password) {
        return new LoginScreen(driver)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}

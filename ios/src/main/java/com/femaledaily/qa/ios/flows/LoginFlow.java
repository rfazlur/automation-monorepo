package com.femaledaily.qa.ios.flows;

import com.femaledaily.qa.ios.screens.HomeScreen;
import com.femaledaily.qa.ios.screens.LoginScreen;
import io.appium.java_client.ios.IOSDriver;

public class LoginFlow {
    
    private final IOSDriver driver;
    
    public LoginFlow(IOSDriver driver) {
        this.driver = driver;
    }
    
    public HomeScreen login(String username, String password) {
        return new LoginScreen(driver)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}

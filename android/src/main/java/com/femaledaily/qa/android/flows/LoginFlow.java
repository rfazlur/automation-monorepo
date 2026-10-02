package com.femaledaily.qa.android.flows;

import com.femaledaily.qa.android.screens.HomeScreen;
import com.femaledaily.qa.android.screens.LoginScreen;
import io.appium.java_client.android.AndroidDriver;

public class LoginFlow {
    
    private final AndroidDriver driver;
    
    public LoginFlow(AndroidDriver driver) {
        this.driver = driver;
    }
    
    public HomeScreen login(String username, String password) {
        return new LoginScreen(driver)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}

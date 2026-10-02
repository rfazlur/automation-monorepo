package com.femaledaily.qa.web.flows;

import com.femaledaily.qa.web.pages.HomePage;
import com.femaledaily.qa.web.pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginFlow {
    
    private final WebDriver driver;
    
    public LoginFlow(WebDriver driver) {
        this.driver = driver;
    }
    
    public HomePage login(String username, String password) {
        return new LoginPage(driver)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}

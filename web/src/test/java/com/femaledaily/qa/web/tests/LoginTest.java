package com.femaledaily.qa.web.tests;

import com.femaledaily.qa.config.ConfigManager;
import com.femaledaily.qa.driver.DriverManager;
import com.femaledaily.qa.listeners.TestListener;
import com.femaledaily.qa.web.flows.LoginFlow;
import com.femaledaily.qa.web.pages.HomePage;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class LoginTest {
    
    @BeforeMethod
    public void setup() {
        String browser = ConfigManager.get("web.browser", "chrome");
        DriverManager.initBrowser(browser);
        String baseUrl = ConfigManager.get("web.baseUrl");
        DriverManager.getDriver().get(baseUrl + "/login");
    }
    
    @Test
    public void testValidLogin() {
        HomePage homePage = new LoginFlow(DriverManager.getDriver())
                .login("testuser@example.com", "password123");
        
        assertTrue(homePage.isWelcomeMessageDisplayed(), "Welcome message should be displayed");
    }
    
    @AfterMethod
    public void teardown() {
        DriverManager.quitDriver();
    }
}

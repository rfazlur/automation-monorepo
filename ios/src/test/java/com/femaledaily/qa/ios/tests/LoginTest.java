package com.femaledaily.qa.ios.tests;

import com.femaledaily.qa.config.ConfigManager;
import com.femaledaily.qa.driver.DriverManager;
import com.femaledaily.qa.ios.flows.LoginFlow;
import com.femaledaily.qa.ios.screens.HomeScreen;
import com.femaledaily.qa.listeners.TestListener;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class LoginTest {
    
    private IOSDriver driver;
    
    @BeforeMethod
    public void setup() throws MalformedURLException {
        String appiumUrl = ConfigManager.get("ios.appium.url", "http://127.0.0.1:4723");
        String deviceName = ConfigManager.get("ios.device.name", "iPhone 16");
        String bundleId = ConfigManager.get("ios.bundle.id", "com.femaledaily.app");
        
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(deviceName)
                .setBundleId(bundleId)
                .setAutomationName("XCUITest");
        
        driver = new IOSDriver(new URL(appiumUrl), options);
        DriverManager.setDriver(driver);
    }
    
    @Test
    public void testValidLogin() {
        HomeScreen homeScreen = new LoginFlow(driver)
                .login("testuser@example.com", "password123");
        
        assertTrue(homeScreen.isWelcomeMessageDisplayed(), "Welcome message should be displayed");
    }
    
    @AfterMethod
    public void teardown() {
        DriverManager.quitDriver();
    }
}

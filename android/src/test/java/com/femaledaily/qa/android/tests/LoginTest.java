package com.femaledaily.qa.android.tests;

import com.femaledaily.qa.android.flows.LoginFlow;
import com.femaledaily.qa.android.screens.HomeScreen;
import com.femaledaily.qa.config.ConfigManager;
import com.femaledaily.qa.driver.DriverManager;
import com.femaledaily.qa.listeners.TestListener;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class LoginTest {
    
    private AndroidDriver driver;
    
    @BeforeMethod
    public void setup() throws MalformedURLException {
        String appiumUrl = ConfigManager.get("android.appium.url", "http://127.0.0.1:4723");
        String deviceName = ConfigManager.get("android.device.name", "emulator-5554");
        String appPackage = ConfigManager.get("android.app.package", "com.femaledaily.app");
        String appActivity = ConfigManager.get("android.app.activity", ".MainActivity");
        
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(deviceName)
                .setAppPackage(appPackage)
                .setAppActivity(appActivity)
                .setAutomationName("UiAutomator2");
        
        driver = new AndroidDriver(new URL(appiumUrl), options);
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

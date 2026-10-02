package com.femaledaily.qa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static Properties properties;
    
    static {
        loadConfig();
    }
    
    private static void loadConfig() {
        String env = System.getProperty("env", "dev");
        String configFile = "config/" + env + ".properties";
        
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            log.info("Loaded config: {}", configFile);
        } catch (IOException e) {
            log.error("Failed to load config: {}", configFile, e);
            throw new RuntimeException("Configuration file not found: " + configFile);
        }
    }
    
    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = properties.getProperty(key);
        }
        return value;
    }
    
    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}

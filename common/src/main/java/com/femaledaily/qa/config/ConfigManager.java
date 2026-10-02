package com.femaledaily.qa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {
    
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static Properties properties;
    
    static {
        loadConfig();
    }
    
    private static void loadConfig() {
        String env = System.getProperty("env", "dev");
        String fileName = "config/" + env + ".properties";
        Path configPath = resolveFromProjectRoot(fileName);
        
        properties = new Properties();
        try (InputStream is = Files.newInputStream(configPath)) {
            properties.load(is);
            log.info("Loaded config: {}", configPath);
        } catch (IOException e) {
            log.error("Failed to load config: {}", configPath, e);
            throw new RuntimeException("Configuration file not found: " + configPath);
        }
    }
    
    private static Path resolveFromProjectRoot(String relativePath) {
        Path dir = Paths.get(System.getProperty("user.dir"));
        while (dir != null) {
            Path candidate = dir.resolve(relativePath);
            if (Files.exists(candidate)) return candidate;
            dir = dir.getParent();
        }
        return Paths.get(relativePath);
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

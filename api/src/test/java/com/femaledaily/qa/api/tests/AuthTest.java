package com.femaledaily.qa.api.tests;

import com.femaledaily.qa.api.client.AuthClient;
import com.femaledaily.qa.listeners.TestListener;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners(TestListener.class)
public class AuthTest {
    
    private final AuthClient authClient = new AuthClient();
    
    @Test
    public void testLoginSuccess() {
        Response response = authClient.login("testuser@example.com", "password123");
        
        assertEquals(response.statusCode(), 200, "Status code should be 200");
        assertEquals(response.jsonPath().getString("status"), "success", "Status should be success");
    }
    
    @Test
    public void testLoginInvalidCredentials() {
        Response response = authClient.login("invalid@example.com", "wrongpassword");
        
        assertEquals(response.statusCode(), 401, "Status code should be 401");
    }
}

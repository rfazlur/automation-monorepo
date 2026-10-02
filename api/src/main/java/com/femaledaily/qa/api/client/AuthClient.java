package com.femaledaily.qa.api.client;

import com.femaledaily.qa.api.models.LoginRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {
    
    public Response login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        
        return given()
                .spec(BaseApiClient.requestSpec())
                .body(request)
                .when()
                .post("/auth/login");
    }
    
    public Response getUserProfile(String token) {
        return given()
                .spec(BaseApiClient.requestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/profile");
    }
}

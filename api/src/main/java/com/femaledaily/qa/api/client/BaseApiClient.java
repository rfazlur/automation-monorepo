package com.femaledaily.qa.api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import com.femaledaily.qa.config.ConfigManager;

public class BaseApiClient {
    
    public static RequestSpecification requestSpec() {
        String baseUrl = ConfigManager.get("api.baseUrl");
        
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}

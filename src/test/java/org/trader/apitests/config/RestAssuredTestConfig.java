package org.trader.apitests.config;

import io.restassured.RestAssured;

import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.config.HttpClientConfig.httpClientConfig;

public class RestAssuredTestConfig
{
    public static void configure()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.basePath = "/api/v1";

        RestAssured.config = config().httpClient(httpClientConfig()
                .setParam("http.connection.timeout", 3000)
                .setParam("http.socket.timeout", 3000));
    }
}

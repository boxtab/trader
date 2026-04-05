package org.trader.apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class ApiTestHelper
{
    private static String traderToken;
    private static String managerToken;

    public String getTraderToken()
    {
        if (traderToken == null) {
            traderToken = loginTrader();
        }
        return traderToken;
    }

    public String getManagerToken()
    {
        if (managerToken == null) {
            managerToken = loginManager();
        }
        return managerToken;
    }

    private String loginTrader()
    {
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                            "login": "109478490",
                            "password": "12345zZ!ABC"
                        }
                        """)
                .when()
                .post("/login");

        if (response.statusCode() != 200) {
            throw new IllegalStateException("Не удалось залогиниться трейдером: " + response.body().asString());
        }

        return response.jsonPath().getString("data.access_token");
    }

    private String loginManager()
    {
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                            "login": "User1@user.com",
                            "password": "12345zZ!ABC"
                        }
                        """)
                .when()
                .post("/manager/login");

        if (response.statusCode() != 200) {
            throw new IllegalStateException("Не удалось залогиниться менеджером: " + response.body().asString());
        }

        return response.jsonPath().getString("acess_token.plainTextToken");
    }
}

package org.trader.apitests.manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured;
import org.trader.apitests.config.RestAssuredTestConfig;

@SpringBootTest
public class OrderCloseCustomPriceTest
{
    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test void testCloseCustomPrice() { /* TODO: POST /close-custom-price */ }
}

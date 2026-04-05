package org.trader.apitests.trader;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.trader.apitests.ApiTestHelper;
import org.trader.apitests.config.RestAssuredTestConfig;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderBuySellTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testBuySell() throws Exception
    {
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderBuySellTest---");

        String body = """
                {
                    "quote_id": 3,
                    "order_type": 0,
                    "order_state": 1,
                    "currency_id": 1,
                    "open_price": 2.232,
                    "units": 1
                }
                """;

        System.out.println("Отправляем запрос на /order/buy-sell с телом:");
        System.out.println(body);

        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/order/buy-sell")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.asString();
        System.out.println("Получен ответ от сервера:");
        System.out.println(responseBody);

        String status = response.jsonPath().getString("status");
        Integer orderId = response.jsonPath().getInt("data.order_id");

        System.out.println("Статус ответа: " + status);
        System.out.println("ID созданного ордера: " + orderId);

        assertThat(status).isEqualTo("ok");
        assertThat(orderId).isNotNull().isGreaterThan(0);

        System.out.println("Тест завершен успешно! Ордер создан с ID: " + orderId);
    }
}

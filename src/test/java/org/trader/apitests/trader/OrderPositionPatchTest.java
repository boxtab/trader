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
public class OrderPositionPatchTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testUpdatePositionOrder() throws Exception
    {
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderPositionPatchTest---");

        // шаг 1: создаём ордер в состоянии POSITION
        String createRequest = """
                {
                  "quote_id": 3,
                  "order_type": 0,
                  "order_state": 1,
                  "currency_id": 1,
                  "open_price": 2.232,
                  "units": 1
                }
                """;

        System.out.println("ШАГ 1: Отправляем POST запрос на /order/buy-sell для создания позиционного ордера:");
        System.out.println(createRequest);

        var createResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(createRequest)
                .when()
                .post("/order/buy-sell")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String createResponseBody = createResponse.asString();
        System.out.println("Ответ от /order/buy-sell:");
        System.out.println(createResponseBody);

        Integer orderId = createResponse.jsonPath().getInt("data.order_id");
        System.out.println("ID созданного позиционного ордера: " + orderId);

        assertThat(orderId).isNotNull().isGreaterThan(0);

        // шаг 2: обновляем позиционный ордер
        String patchRequest = """
                {
                    "stop_loss": {
                        "key": 3,
                        "value": 45
                    },
                    "take_profit": {
                        "key": null,
                        "value": null
                    }
                }
                """;

        System.out.println("\nШАГ 2: Отправляем PATCH запрос на /order/position/" + orderId + " с телом:");
        System.out.println(patchRequest);

        var patchResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(patchRequest)
                .when()
                .patch("/order/position/" + orderId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String patchResponseBody = patchResponse.asString();
        System.out.println("Ответ от /order/position/" + orderId + ":");
        System.out.println(patchResponseBody);

        String status = patchResponse.jsonPath().getString("status");
        String data = patchResponse.jsonPath().getString("data");

        System.out.println("Статус обновления: " + status);
        System.out.println("Данные ответа: " + data);

        // шаг 3: проверки
        assertThat(status).isEqualTo("ok");
        assertThat(data).isNotBlank()
                .contains("updated");

        System.out.println("\nТест завершен успешно! Позиционный ордер с ID " + orderId + " создан и обновлен");
    }
}

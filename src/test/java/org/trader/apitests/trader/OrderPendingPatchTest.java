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
public class OrderPendingPatchTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testUpdatePendingOrder() throws Exception
    {
        // получаем токен трейдера
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderPendingPatchTest---");

        // шаг 1: создаём pending ордер
        String pendingOrderRequest = """
                {
                  "quote_id": 3,
                  "order_type": 0,
                  "order_state": 3,
                  "currency_id": 1,
                  "open_price": 2.232,
                  "units": 1
                }
                """;

        System.out.println("ШАГ 1: Отправляем POST запрос на /order/buy-sell для создания pending ордера:");
        System.out.println(pendingOrderRequest);

        var createResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(pendingOrderRequest)
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
        System.out.println("ID созданного pending ордера: " + orderId);

        assertThat(orderId).isNotNull().isGreaterThan(0);

        // шаг 2: обновляем pending ордер через PATCH
        String updateRequest = """
                {
                    "open_price": 8,
                    "expired": "2025-04-07 14:30:15",
                    "activation_price": null,
                    "stop_loss": {
                        "key": 1,
                        "value": 17.2587
                    },
                    "take_profit": {
                        "key": 1,
                        "value": 25.1458
                    }
                }
                """;

        System.out.println("\nШАГ 2: Отправляем PATCH запрос на /order/pending/" + orderId + " с телом:");
        System.out.println(updateRequest);

        var patchResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(updateRequest)
                .when()
                .patch("/order/pending/" + orderId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String patchResponseBody = patchResponse.asString();
        System.out.println("Ответ от /order/pending/" + orderId + ":");
        System.out.println(patchResponseBody);

        String status = patchResponse.jsonPath().getString("status");
        String data = patchResponse.jsonPath().getString("data");

        System.out.println("Статус обновления: " + status);
        System.out.println("Данные ответа: " + data);

        // шаг 3: проверки
        assertThat(status).isEqualTo("ok");
        assertThat(data).isNotBlank()
                .contains("updated"); // в ответе есть фраза про обновление

        System.out.println("\nТест завершен успешно! Pending ордер с ID " + orderId + " создан и обновлен");
    }
}

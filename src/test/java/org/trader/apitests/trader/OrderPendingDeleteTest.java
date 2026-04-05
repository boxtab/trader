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
public class OrderPendingDeleteTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testDeletePendingOrder() throws Exception
    {
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderPendingDeleteTest---");

        // шаг 1: создаём ордер в состоянии "pending"
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

        // шаг 2: удаляем созданный pending ордер
        String deleteRequest = """
                {
                  "orderIDs": [%d]
                }
                """.formatted(orderId);

        System.out.println("\nШАГ 2: Отправляем DELETE запрос на /order/pending/delete с телом:");
        System.out.println(deleteRequest);

        var deleteResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(deleteRequest)
                .when()
                .delete("/order/pending/delete")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String deleteResponseBody = deleteResponse.asString();
        System.out.println("Ответ от /order/pending/delete:");
        System.out.println(deleteResponseBody);

        String status = deleteResponse.jsonPath().getString("status");
        String data = deleteResponse.jsonPath().getString("data");

        System.out.println("Статус удаления: " + status);
        System.out.println("Данные ответа: " + data);

        // шаг 3: проверяем ответ
        assertThat(status).isEqualTo("ok");
        assertThat(data).isNotBlank()
                .contains("deletePendingOrdersByTrader");

        System.out.println("\nТест завершен успешно! Pending ордер с ID " + orderId + " создан и удален");
    }
}

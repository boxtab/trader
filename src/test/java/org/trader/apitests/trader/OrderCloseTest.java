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
public class OrderCloseTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testBuyAndCloseOrder() throws Exception
    {
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderCloseTest---");

        // шаг 1: создаём ордер (buy-sell)
        String buyRequest = """
                {
                  "quote_id": 3,
                  "order_type": 0,
                  "order_state": 1,
                  "currency_id": 1,
                  "open_price": 2.232,
                  "units": 1
                }
                """;

        System.out.println("ШАГ 1: Отправляем POST запрос на /order/buy-sell с телом:");
        System.out.println(buyRequest);

        var buyResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(buyRequest)
                .when()
                .post("/order/buy-sell")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String buyResponseBody = buyResponse.asString();
        System.out.println("Ответ от /order/buy-sell:");
        System.out.println(buyResponseBody);

        String statusBuy = buyResponse.jsonPath().getString("status");
        Integer orderId = buyResponse.jsonPath().getInt("data.order_id");

        System.out.println("Статус создания ордера: " + statusBuy);
        System.out.println("ID созданного ордера: " + orderId);

        assertThat(statusBuy).isEqualTo("ok");
        assertThat(orderId).isNotNull().isGreaterThan(0);

        // шаг 2: закрываем созданный ордер
        String closeRequest = """
                {
                  "orderIDs": [%d]
                }
                """.formatted(orderId);

        System.out.println("\nШАГ 2: Отправляем POST запрос на /order/close с телом:");
        System.out.println(closeRequest);

        var closeResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(closeRequest)
                .when()
                .post("/order/close")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String closeResponseBody = closeResponse.asString();
        System.out.println("Ответ от /order/close:");
        System.out.println(closeResponseBody);

        String statusClose = closeResponse.jsonPath().getString("status");
        Integer closedCount = closeResponse.jsonPath().getInt("data");

        System.out.println("Статус закрытия ордера: " + statusClose);
        System.out.println("Количество закрытых ордеров: " + closedCount);

        assertThat(statusClose).isEqualTo("ok");
        assertThat(closedCount).isNotNull().isGreaterThanOrEqualTo(1);

        System.out.println("\nТест завершен успешно! Создан и закрыт ордер с ID: " + orderId);
    }
}

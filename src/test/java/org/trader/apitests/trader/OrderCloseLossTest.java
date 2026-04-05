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
public class OrderCloseLossTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testCloseLossOrders() throws Exception
    {
        String token = apiTestHelper.getTraderToken();
        System.out.println("---OrderCloseLossTest---");

        System.out.println("Отправляем GET запрос на /order/close-loss");

        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/order/close-loss")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.asString();
        System.out.println("Получен ответ от сервера:");
        System.out.println(responseBody);

        String status = response.jsonPath().getString("status");
        Integer data = response.jsonPath().getInt("data");

        System.out.println("Статус ответа: " + status);
        System.out.println("Количество закрытых убыточных ордеров: " + data);

        assertThat(status).isEqualTo("ok");
        assertThat(data).isNotNull().isGreaterThanOrEqualTo(0);

        System.out.println("Тест завершен успешно! Закрыто убыточных ордеров: " + data);
    }
}

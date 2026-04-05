package org.trader.apitests.manager;

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
public class BalanceTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testGetBalance() throws Exception
    {
        String token = apiTestHelper.getManagerToken();
        int accountId = 3;

        System.out.println("---BalanceTest---");
        System.out.println("Отправляем запрос на /manager/balance/" + accountId);

        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/manager/balance/" + accountId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String responseBody = response.asString();
        System.out.println("Ответ от сервера:");
        System.out.println(responseBody);

        String status = response.jsonPath().getString("status");
        Integer returnedAccountId = response.jsonPath().getInt("data.account_id");

        System.out.println("Статус ответа: " + status);
        System.out.println("ID аккаунта из ответа: " + returnedAccountId);

        // Проверки
        assertThat(status).isEqualTo("ok");
        assertThat(returnedAccountId).isEqualTo(accountId);

        System.out.println("Тест успешно завершен! Баланс получен для аккаунта " + accountId);
    }
}

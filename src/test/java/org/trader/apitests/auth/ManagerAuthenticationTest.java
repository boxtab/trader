package org.trader.apitests.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.trader.apitests.ApiTestHelper;
import org.trader.apitests.config.RestAssuredTestConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ManagerAuthenticationTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testManagerAuthenticationSuccess() throws Exception
    {
        // Получаем токен менеджера через хэлпер
        String token = apiTestHelper.getManagerToken();

        // Проверяем, что токен получен и валиден
        assertThat(token)
                .isNotNull()
                .isNotBlank()
                .isNotEqualTo("null")
                .hasSizeGreaterThan(20); // JWT токены обычно длинные

        System.out.println("Менеджер успешно аутентифицирован. Токен: " + token);
    }
}

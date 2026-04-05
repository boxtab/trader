package org.trader.apitests.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.trader.apitests.ApiTestHelper;
import org.trader.apitests.config.RestAssuredTestConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraderAuthenticationTest
{
    @Autowired
    private ApiTestHelper apiTestHelper;

    @BeforeAll
    static void setup()
    {
        RestAssuredTestConfig.configure();
    }

    @Test
    void testTraderAuthenticationSuccess() throws Exception
    {
        String token = apiTestHelper.getTraderToken();

        assertThat(token)
                .isNotNull()
                .isNotBlank()
                .isNotEqualTo("null")
                .hasSizeGreaterThan(20);

        System.out.println("Трейдер успешно аутентифицирован. Токен: " + token);
    }
}

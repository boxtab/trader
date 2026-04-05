package org.trader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TraderApplication
{
    private static final Logger logger = LoggerFactory.getLogger(TraderApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(TraderApplication.class, args);
        logger.info("✅ TraderApplication успешно запущено");
    }
}

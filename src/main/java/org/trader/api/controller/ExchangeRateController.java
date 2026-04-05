package org.trader.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class ExchangeRateController
{
    @GetMapping("/exchange-rate")
    public String exchangeRate()
    {
        return "Exchange rate public";
    }

    @GetMapping("/manager/exchange-rate")
    public String exchangeRateManager()
    {
        return "Exchange rate manager";
    }
}

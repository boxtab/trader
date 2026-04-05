package org.trader.api.controller.manager;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/manager/account")
public class AccountManagerController
{
    @GetMapping("/simple-list")
    public String simpleList()
    {
        return "Simple list of accounts";
    }
}

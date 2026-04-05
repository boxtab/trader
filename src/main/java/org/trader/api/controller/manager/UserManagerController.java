package org.trader.api.controller.manager;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/manager/users")
public class UserManagerController
{
    @GetMapping("/simple-list")
    public String simpleList()
    {
        return "Simple list of users";
    }
}

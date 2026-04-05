package org.trader.stub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController
{
    @GetMapping("/")
    public String home() {
        return "Connection speed analysis tool.";
    }
}

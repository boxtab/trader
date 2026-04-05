package org.trader.speedcheck.presentation.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trader.api.config.NoWrap;
import org.trader.speedcheck.application.services.SpeedCheckFacade;

@RestController
public class SpeedCheckController
{
    private final SpeedCheckFacade speedCheckFacade;

    public SpeedCheckController( SpeedCheckFacade speedCheckFacade )
    {
        this.speedCheckFacade = speedCheckFacade;
    }

    @NoWrap
    @GetMapping(value = "/check", produces = "text/html")
    public String check()
    {
        return speedCheckFacade.getFormattedResults();
    }
}

package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Service;
import org.trader.speedcheck.domain.model.SpeedCheckResult;

import java.util.List;

@Service
public class SpeedCheckFacade
{
    private final SpeedCheckService speedCheckService;
    private final SpeedCheckFormatter speedCheckFormatter;

    public SpeedCheckFacade(
            SpeedCheckService speedCheckService,
            SpeedCheckFormatter speedCheckFormatter
    ) {
        this.speedCheckService = speedCheckService;
        this.speedCheckFormatter = speedCheckFormatter;
    }

    public String getFormattedResults()
    {
        List<SpeedCheckResult> results = speedCheckService.getResults();

        return speedCheckFormatter.formatAsHtmlTable( results );
    }
}

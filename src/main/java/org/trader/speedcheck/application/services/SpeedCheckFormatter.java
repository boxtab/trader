package org.trader.speedcheck.application.services;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.trader.speedcheck.domain.model.EndpointCheckResult;
import org.trader.speedcheck.domain.model.SpeedCheckResult;

import java.util.List;

@Component
public class SpeedCheckFormatter
{
    private final SpringTemplateEngine templateEngine;

    public SpeedCheckFormatter( SpringTemplateEngine templateEngine )
    {
        this.templateEngine = templateEngine;
    }

    public String formatAsHtmlTable( List<SpeedCheckResult> speedCheckResultList )
    {
        Context context = new Context();
        context.setVariable( "results", speedCheckResultList );
        context.setVariable( "endpoints", extractUniqueEndpoints( speedCheckResultList ) );

        return templateEngine.process("speed-check-table", context);
    }

    private List<String> extractUniqueEndpoints( List<SpeedCheckResult> speedCheckResultList )
    {
        return speedCheckResultList.stream()
                .flatMap( r -> r.endpointChecks().stream() )
                .map( EndpointCheckResult::endpointPath )
                .distinct()
                .toList();
    }
}

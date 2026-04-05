package org.trader.speedcheck.domain.services;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.EndpointCheck;
import org.trader.speedcheck.domain.model.EndpointDefinition;

import java.util.List;

@Component
public class EndpointTaskBuilder
{
    public List<EndpointCheck> buildTasks(
            String baseUrl,
            List<EndpointDefinition> definitions
    )
    {
        return definitions.stream()
                .map(def -> createTask(baseUrl, def))
                .toList();
    }

    private EndpointCheck createTask(String baseUrl, EndpointDefinition def)
    {
        return new EndpointCheck(
                def.getPath(),
                baseUrl + def.getPath(),
                def.getAuthType(),
                def.getMethod(),
                def.getBody()
        );
    }
}

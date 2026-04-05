package org.trader.speedcheck.application.services.strategies;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.AuthType;
import org.trader.speedcheck.domain.ports.TokenExtractorStrategy;

import java.util.Map;
import java.util.Optional;

@Component
public class TraderTokenExtractor implements TokenExtractorStrategy
{
    @Override
    public AuthType getType()
    {
        return AuthType.TRADER;
    }

    @Override
    public String extract( Map<String, Object> body )
    {
        return Optional.ofNullable(body.get("data"))
                .map(
                        data -> ((Map<?, ?>) data).get("access_token")
                )
                .map(String::valueOf)
                .orElse(null);
    }
}

package org.trader.speedcheck.application.services.strategies;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.AuthType;
import org.trader.speedcheck.domain.ports.TokenExtractorStrategy;

import java.util.Map;
import java.util.Optional;

@Component
public class ManagerTokenExtractor implements TokenExtractorStrategy
{
    @Override
    public AuthType getType()
    {
        return AuthType.MANAGER;
    }

    @Override
    public String extract( Map<String, Object> body )
    {
        return Optional.ofNullable( body.get("acess_token") ) // опечатка сохранена
                .map(
                        access -> ((Map<?, ?>) access).get("plainTextToken")
                )
                .map(String::valueOf)
                .orElse(null);
    }
}

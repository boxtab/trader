package org.trader.speedcheck.domain.ports;

import org.trader.speedcheck.domain.model.AuthType;

import java.util.Map;

public interface TokenExtractorStrategy
{
    AuthType getType();

    String extract( Map<String, Object> body );
}

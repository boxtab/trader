package org.trader.speedcheck.domain.model;

import lombok.Data;

@Data
public class EndpointDefinition
{
    private String path;
    private AuthType authType;
    private String method = "GET";   // по умолчанию GET
    private String body   = "";      // по умолчанию пустое тело
}

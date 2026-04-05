package org.trader.speedcheck.domain.model;

import lombok.Getter;

@Getter
public class ServerEntry
{
    private String name;
    private String baseUrl;

    private String traderLogin;
    private String traderPassword;

    private String managerLogin;
    private String managerPassword;
}

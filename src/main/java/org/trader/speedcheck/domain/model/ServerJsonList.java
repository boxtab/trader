package org.trader.speedcheck.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ServerJsonList
{
    private List<ServerEntry> servers;
}

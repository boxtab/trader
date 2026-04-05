package org.trader.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortRequest
{
    private String column;
    private String method;
}

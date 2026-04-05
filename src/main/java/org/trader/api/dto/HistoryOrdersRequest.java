package org.trader.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryOrdersRequest
{
    @Pattern(regexp = "buy|sell", message = "filter_type must be 'buy' or 'sell'")
    private String filterType;

    private String sortColumn;

    @Pattern(regexp = "asc|desc", message = "sort.method must be 'asc' or 'desc'")
    private String sortMethod;

    @Min(0)
    private Integer perPage = 20;

    @Min(0)
    private Integer page = 0;
}

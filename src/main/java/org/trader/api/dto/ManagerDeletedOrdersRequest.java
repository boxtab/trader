package org.trader.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDeletedOrdersRequest
{
    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("filter_type")
    private String filterType;

    private SortRequest sort;

    @JsonProperty("per_page")
    @Min(0)
    private Integer perPage = 20;

    @JsonProperty("page")
    @Min(1)
    private Integer page = 1;
}

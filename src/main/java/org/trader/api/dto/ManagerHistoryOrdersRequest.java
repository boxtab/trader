package org.trader.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerHistoryOrdersRequest extends HistoryOrdersRequest {

    @JsonProperty("filter_account_id")
    @Min(value = 1, message = "filter_account_id must be >= 1")
    private Long filterAccountId;
}

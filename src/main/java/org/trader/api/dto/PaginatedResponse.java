package org.trader.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedResponse<T>
{
    private List<T> data;
    private int current_page;
    private int per_page;
    private long total;

    public PaginatedResponse(List<T> data, int currentPage, int perPage, long total)
    {
        this.data = data;
        this.current_page = currentPage;
        this.per_page = perPage;
        this.total = total;
    }
}

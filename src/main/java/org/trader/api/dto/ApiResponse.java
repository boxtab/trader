package org.trader.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T>
{
    private T data;
    private String status;

    public ApiResponse(T data)
    {
        this.data = data;
        this.status = "ok";
    }

    public static <T> ApiResponse<T> ok(T data)
    {
        return new ApiResponse<>(data);
    }
}

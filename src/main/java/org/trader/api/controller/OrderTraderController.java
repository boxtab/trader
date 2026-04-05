package org.trader.api.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.trader.api.config.NoWrap;
import org.trader.api.dto.*;
import org.trader.api.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/order")
public class OrderTraderController
{
    private final OrderService orderService;

    public OrderTraderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping("/position/list")
    public List<PositionOrderDto> listPositionOrders(
            @RequestHeader("X-Account-ID") Long accountId)
    {
        return orderService.getPositionOrdersByAccount(accountId);
    }

    @GetMapping("/pending/list")
    public List<PendingOrderDto> listPendingOrders(
            @RequestHeader("X-Account-ID") Long accountId)
    {
        return orderService.getPendingOrdersByAccount(accountId);
    }

    @NoWrap
    @PostMapping("/history/list")
    public PaginatedResponse<HistoryOrderDto> listHistoryOrders(
            @RequestHeader("X-Account-ID") Long accountId,
            @RequestBody @Valid HistoryOrdersRequest request)
    {
        return orderService.getHistoryOrdersByAccount(accountId, request);
    }
}

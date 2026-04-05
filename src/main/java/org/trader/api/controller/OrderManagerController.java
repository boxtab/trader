package org.trader.api.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.trader.api.config.NoWrap;
import org.trader.api.dto.*;
import org.trader.api.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/manager/order")
public class OrderManagerController
{
    private final OrderService orderService;

    public OrderManagerController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping({"/position/list", "/position/list/{accountId}"})
    public List<PositionOrderDto> listPositionOrders(
            @PathVariable(required = false) Long accountId)
    {
        return orderService.getPositionOrders(accountId);
    }

    @GetMapping({"/pending/list", "/pending/list/{accountId}"})
    public List<PendingOrderDto> listPendingOrders(
            @PathVariable(required = false) Long accountId) // Принимаем accountId как PathVariable
    {
        // Вызываем новый (или модифицированный) метод сервиса
        return orderService.getPendingOrders(accountId);
    }

    @NoWrap
    @PostMapping("/history/list")
    public PaginatedResponse<HistoryOrderDto> listHistoryOrdersForManager(
            @RequestBody @Valid ManagerHistoryOrdersRequest request)
    {
        return orderService.getHistoryOrdersForManager(request);
    }

    @NoWrap
    @PostMapping("/deleted/list")
    public PaginatedResponse<HistoryOrderDto> listDeletedOrdersForManager(
            @RequestBody @Valid ManagerDeletedOrdersRequest request)
    {
        return orderService.getDeletedOrdersForManager(request);
    }
}

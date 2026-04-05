package org.trader.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trader.api.dto.*;
import org.trader.api.entity.Order;
import org.trader.api.entity.OrderState;
import org.trader.api.entity.OrderType;
import org.trader.api.mapper.OrderMapper;
import org.trader.api.repository.OrderRepository;
import org.trader.api.util.SortColumnMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper)
    {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<PositionOrderDto> getPositionOrders(Long accountId)
    {
        if (accountId != null) {
            // фильтруем по accountId
            return orderRepository.findByAccountIdAndStateAndDeletedAtIsNull(accountId, OrderState.POSITION)
                    .stream()
                    .map(orderMapper::orderToPositionDto)
                    .collect(Collectors.toList());
        }

        // если accountId не указан — возвращаем все позиции
        return orderRepository.findByStateAndDeletedAtIsNull(OrderState.POSITION)
                .stream()
                .map(orderMapper::orderToPositionDto)
                .collect(Collectors.toList());
    }

    public List<PositionOrderDto> getPositionOrdersByAccount(Long accountId)
    {
        return orderRepository.findByAccountIdAndStateAndDeletedAtIsNull(accountId, OrderState.POSITION)
                .stream()
                .map(orderMapper::orderToPositionDto)
                .collect(Collectors.toList());
    }

    public List<PendingOrderDto> getPendingOrdersByAccount(Long accountId)
    {
        return orderRepository.findByAccountIdAndStateAndDeletedAtIsNull(accountId, OrderState.PENDING)
                .stream()
                .map(orderMapper::orderToPendingDto)
                .collect(Collectors.toList());
    }

    public List<PendingOrderDto> getPendingOrders(Long accountId)
    {
        if (accountId != null) {
            // Если accountId указан: используем существующий метод репозитория с фильтром
            return orderRepository.findByAccountIdAndStateAndDeletedAtIsNull(accountId, OrderState.PENDING)
                    .stream()
                    .map(orderMapper::orderToPendingDto)
                    .collect(Collectors.toList());
        }

        // Если accountId не указан: возвращаем ВСЕ ожидающие ордера
        return orderRepository.findByStateAndDeletedAtIsNull(OrderState.PENDING)
                .stream()
                .map(orderMapper::orderToPendingDto)
                .collect(Collectors.toList());
    }

    private Page<Order> fetchHistoryOrders(Long accountId, String filterType, Pageable pageable)
    {
        boolean hasAccountFilter = accountId != null;

        if ("buy".equalsIgnoreCase(filterType)) {
            return hasAccountFilter
                    ? orderRepository.findByAccountIdAndStateAndTypeAndDeletedAtIsNull(accountId, OrderState.HISTORY, OrderType.BUY, pageable)
                    : orderRepository.findByStateAndTypeAndDeletedAtIsNull(OrderState.HISTORY, OrderType.BUY, pageable);
        } else if ("sell".equalsIgnoreCase(filterType)) {
            return hasAccountFilter
                    ? orderRepository.findByAccountIdAndStateAndTypeAndDeletedAtIsNull(accountId, OrderState.HISTORY, OrderType.SELL, pageable)
                    : orderRepository.findByStateAndTypeAndDeletedAtIsNull(OrderState.HISTORY, OrderType.SELL, pageable);
        } else {
            return hasAccountFilter
                    ? orderRepository.findByAccountIdAndStateAndDeletedAtIsNull(accountId, OrderState.HISTORY, pageable)
                    : orderRepository.findByStateAndDeletedAtIsNull(OrderState.HISTORY, pageable);
        }
    }

    public PaginatedResponse<HistoryOrderDto> getHistoryOrdersByAccount(Long accountId, HistoryOrdersRequest request)
    {
        Pageable pageable = buildPageable(request);
        Page<Order> orders = fetchHistoryOrders(accountId, request.getFilterType(), pageable);

        List<HistoryOrderDto> mapped = orders.map(orderMapper::orderToHistoryDto).getContent();

        return new PaginatedResponse<>(
                mapped,
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                orders.getTotalElements()
        );
    }

    public PaginatedResponse<HistoryOrderDto> getHistoryOrdersForManager(ManagerHistoryOrdersRequest request)
    {
        Pageable pageable = buildPageable(request);
        Page<Order> orders = fetchHistoryOrders(request.getFilterAccountId(), request.getFilterType(), pageable);

        List<HistoryOrderDto> mapped = orders.map(orderMapper::orderToHistoryDto).getContent();

        return new PaginatedResponse<>(
                mapped,
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                orders.getTotalElements()
        );
    }

    private Pageable buildPageable(HistoryOrdersRequest request)
    {
        int perPage = (request.getPerPage() == null || request.getPerPage() <= 0) ? 20 : request.getPerPage();
        int page = (request.getPage() == null || request.getPage() <= 0) ? 0 : request.getPage() - 1;

        String requestedSortColumn = request.getSortColumn();
        String sortColumn = (requestedSortColumn == null || requestedSortColumn.trim().isEmpty())
                ? "id"
                : SortColumnMapper.mapColumn(requestedSortColumn);

        String method = (request.getSortMethod() == null) ? "desc" : request.getSortMethod();
        Sort sort = Sort.by("asc".equalsIgnoreCase(method) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);

        return PageRequest.of(page, perPage, sort);
    }

    public PaginatedResponse<HistoryOrderDto> getDeletedOrdersForManager(ManagerDeletedOrdersRequest request)
    {
        int perPage = request.getPerPage() != null ? request.getPerPage() : 20;
        int page = request.getPage() != null ? request.getPage() - 1 : 0;

        String sortColumn = request.getSort() != null && request.getSort().getColumn() != null
                ? SortColumnMapper.mapColumn(request.getSort().getColumn())
                : "id";

        String method = request.getSort() != null && request.getSort().getMethod() != null
                ? request.getSort().getMethod()
                : "asc";

        Sort sort = Sort.by("asc".equalsIgnoreCase(method) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Page<Order> orders;

        boolean hasAccount = request.getAccountId() != null;
        boolean hasType = request.getFilterType() != null;

        if (hasAccount && hasType) {
            OrderType type = "buy".equalsIgnoreCase(request.getFilterType()) ? OrderType.BUY : OrderType.SELL;
            orders = orderRepository.findByAccountIdAndTypeAndDeletedAtIsNotNull(request.getAccountId(), type, pageable);
        } else if (hasAccount) {
            orders = orderRepository.findByAccountIdAndDeletedAtIsNotNull(request.getAccountId(), pageable);
        } else if (hasType) {
            OrderType type = "buy".equalsIgnoreCase(request.getFilterType()) ? OrderType.BUY : OrderType.SELL;
            orders = orderRepository.findByTypeAndDeletedAtIsNotNull(type, pageable);
        } else {
            orders = orderRepository.findByDeletedAtIsNotNull(OrderState.HISTORY, pageable);
        }

        List<HistoryOrderDto> mapped = orders.map(orderMapper::orderToHistoryDto).getContent();

        return new PaginatedResponse<>(
                mapped,
                page + 1,
                perPage,
                orders.getTotalElements()
        );
    }
}

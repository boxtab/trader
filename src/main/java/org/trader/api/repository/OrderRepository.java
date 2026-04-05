package org.trader.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trader.api.entity.Order;
import org.trader.api.entity.OrderState;
import org.trader.api.entity.OrderType;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByStateAndDeletedAtIsNull(OrderState state);

    List<Order> findByAccountIdAndStateAndDeletedAtIsNull(Long accountId, OrderState state);

    Page<Order> findByAccountIdAndStateAndDeletedAtIsNull(
            Long accountId,
            OrderState state,
            Pageable pageable
    );

    Page<Order> findByAccountIdAndStateAndTypeAndDeletedAtIsNull(
            Long accountId,
            OrderState state,
            OrderType type,
            Pageable pageable
    );

    Page<Order> findByStateAndDeletedAtIsNull(
            OrderState state,
            Pageable pageable
    );

    Page<Order> findByStateAndTypeAndDeletedAtIsNull(
            OrderState state,
            OrderType type,
            Pageable pageable
    );

    // /api/v2/manager/order/deleted/list
    Page<Order> findByDeletedAtIsNotNull(OrderState state, Pageable pageable);

    Page<Order> findByAccountIdAndDeletedAtIsNotNull(Long accountId, Pageable pageable);

    Page<Order> findByAccountIdAndTypeAndDeletedAtIsNotNull(Long accountId, OrderType type, Pageable pageable);

    Page<Order> findByTypeAndDeletedAtIsNotNull(OrderType type, Pageable pageable);
}

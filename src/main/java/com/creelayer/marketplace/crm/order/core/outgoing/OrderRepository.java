package com.creelayer.marketplace.crm.order.core.outgoing;

import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;
import com.creelayer.marketplace.crm.order.core.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends DynamicProjectionHandler {

    Optional<Order> findById(UUID uuid);

    Order save(Order order);

}

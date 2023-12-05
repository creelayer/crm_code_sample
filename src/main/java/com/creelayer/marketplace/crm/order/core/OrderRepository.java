package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends OrderSearch {

    Optional<Order> findById(UUID uuid);

    Order save(Order order);

}

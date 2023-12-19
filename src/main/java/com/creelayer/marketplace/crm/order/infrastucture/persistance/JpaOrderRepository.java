package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import com.creelayer.marketplace.crm.order.core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID>, OrderRepository {
}

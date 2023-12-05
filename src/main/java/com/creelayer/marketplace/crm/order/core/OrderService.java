package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.command.AddOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.command.RemoveOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderStatusCommand;
import com.creelayer.marketplace.crm.order.core.support.OrderItemConvertFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OrderService implements OrderManageProcess {

    private final OrderRepository orderRepository;

    private final CheckoutPromoCodeProvider promoCodeProvider;

    @Override
    public Order manage(UpdateOrderStatusCommand command) {
        Order order = orderRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(command.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public Order confirmPayout(UUID uuid) {
        Order order = orderRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setPayoutStatus(Order.PayoutStatus.COMPLETE);
        return orderRepository.save(order);
    }

    @Override
    public Order addItem(AddOrderItemCommand command) {

        Order order = orderRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        OrderItemConvertFactory factory = new OrderItemConvertFactory(promoCodeProvider, command);
        order.addItem(factory.build());
        return orderRepository.save(order);
    }

    @Override
    public Order updateItemCount(UpdateOrderItemCountCommand command) {
        Order order = orderRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.updateItemCount(command.getSku(), command.getCount());
        return orderRepository.save(order);
    }

    @Override
    public Order removeItem(RemoveOrderItemCountCommand command) {
        Order order = orderRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.removeItem(command.getSku());
        return orderRepository.save(order);
    }
}

package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.incoming.OrderManageProcess;
import com.creelayer.marketplace.crm.order.core.command.AddOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.command.RemoveOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderStatusCommand;
import com.creelayer.marketplace.crm.order.core.outgoing.CheckoutPromoCodeProvider;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import com.creelayer.marketplace.crm.order.core.support.OrderItemConvertFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OrderService implements OrderManageProcess {

    private final OrderRepository orderRepository;

    private final CheckoutPromoCodeProvider promoCodeProvider;

    @Override
    public void manage(UpdateOrderStatusCommand command) {
        Order order = orderRepository.findById(command.getOrder())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(command.getStatus());
        orderRepository.save(order);
    }

    @Override
    public void confirmPayout(UUID uuid) {
        Order order = orderRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setPayoutStatus(Order.PayoutStatus.COMPLETE);
        orderRepository.save(order);
    }

    @Override
    public void addItem(AddOrderItemCommand command) {

        Order order = orderRepository.findById(command.getOrder())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        OrderItemConvertFactory factory = new OrderItemConvertFactory(promoCodeProvider, command);
        order.addItem(factory.build());
        orderRepository.save(order);
    }

    @Override
    public void updateItemCount(UpdateOrderItemCountCommand command) {
        Order order = orderRepository.findById(command.getOrder())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.updateItemCount(command.getSku(), command.getCount());
        orderRepository.save(order);
    }

    @Override
    public void removeItem(RemoveOrderItemCommand command) {
        Order order = orderRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.removeItem(command.getSku());
        orderRepository.save(order);
    }
}

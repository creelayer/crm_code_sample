package com.creelayer.marketplace.crm.order.core.support;

import com.creelayer.marketplace.crm.order.core.outgoing.CheckoutPromoCodeProvider;
import com.creelayer.marketplace.crm.order.core.command.AddOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@RequiredArgsConstructor
public class OrderItemConvertFactory {

    private final CheckoutPromoCodeProvider promoCodeProvider;

    private final AddOrderItemCommand command;

    public OrderItem build() {

        OrderItem item = new OrderItem(
                command.getSku(),
                command.getName(),
                command.getUrl(),
                command.getAmount(),
                command.getCount()
        );

        item.setImage(command.getImage());

        if (command.getPromoCode() != null)
            item.setPromoCode(promoCodeProvider.resolve(command.getPromoCode()));

        return item;
    }
}
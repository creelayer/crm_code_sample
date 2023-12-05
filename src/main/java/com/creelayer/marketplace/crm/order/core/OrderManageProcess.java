package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.command.*;
import com.creelayer.marketplace.crm.order.core.model.Order;

import java.util.UUID;

public interface OrderManageProcess {
    Order manage(UpdateOrderStatusCommand command);

    Order confirmPayout(UUID uuid);

    Order addItem(AddOrderItemCommand command);

    Order updateItemCount(UpdateOrderItemCountCommand command);

    Order removeItem(RemoveOrderItemCountCommand command);
}

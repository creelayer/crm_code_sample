package com.creelayer.marketplace.crm.order.core.incoming;

import com.creelayer.marketplace.crm.order.core.command.*;
import com.creelayer.marketplace.crm.order.core.model.Order;

import java.util.UUID;

public interface OrderManageProcess {
    void manage(UpdateOrderStatusCommand command);

    void confirmPayout(UUID uuid);

    void addItem(AddOrderItemCommand command);

    void updateItemCount(UpdateOrderItemCountCommand command);

    void removeItem(RemoveOrderItemCommand command);
}

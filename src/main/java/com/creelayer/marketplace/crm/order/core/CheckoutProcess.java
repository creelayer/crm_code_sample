package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.Realm;

public interface CheckoutProcess {
    Order checkout(Realm realm, CheckoutCommand command);
}

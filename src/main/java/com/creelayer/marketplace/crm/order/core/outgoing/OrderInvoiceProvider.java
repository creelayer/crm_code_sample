package com.creelayer.marketplace.crm.order.core.outgoing;

import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.OrderInvoice;

public interface OrderInvoiceProvider {

    OrderInvoice createInvoice(Order order);

}

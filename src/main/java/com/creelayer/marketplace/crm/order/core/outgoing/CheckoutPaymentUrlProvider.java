package com.creelayer.marketplace.crm.order.core.outgoing;

import com.creelayer.marketplace.crm.order.core.model.Order;

public interface CheckoutPaymentUrlProvider {
    String createExternalPaymentUrl(Order order);
}

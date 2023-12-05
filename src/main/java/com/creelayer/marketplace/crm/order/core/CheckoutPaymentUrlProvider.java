package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.model.Order;

public interface CheckoutPaymentUrlProvider {
    String createExternalPaymentUrl(Order order);
}

package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.model.OrderPromoCode;

public interface CheckoutPromoCodeProvider {
    OrderPromoCode resolve(String code);
}

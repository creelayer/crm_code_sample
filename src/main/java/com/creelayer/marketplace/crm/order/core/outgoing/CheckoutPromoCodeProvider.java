package com.creelayer.marketplace.crm.order.core.outgoing;

import com.creelayer.marketplace.crm.order.core.model.OrderPromoCode;

public interface CheckoutPromoCodeProvider {
    OrderPromoCode resolve(String code);
}

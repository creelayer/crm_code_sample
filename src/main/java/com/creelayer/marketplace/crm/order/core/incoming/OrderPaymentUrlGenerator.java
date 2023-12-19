package com.creelayer.marketplace.crm.order.core.incoming;


import java.util.UUID;

public interface OrderPaymentUrlGenerator {
    String getExternalPaymentUrl(UUID uuid);
}

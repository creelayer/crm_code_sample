package com.creelayer.marketplace.crm.order.core;


import java.util.UUID;

public interface OrderPaymentUrlGenerator {
    String getExternalPaymentUrl(UUID uuid);
}

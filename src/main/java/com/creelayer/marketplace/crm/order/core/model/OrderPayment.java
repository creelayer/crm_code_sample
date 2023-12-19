package com.creelayer.marketplace.crm.order.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class OrderPayment {

    public enum Type {
        NONE, CASH, CARD, TRANSFER
    }

    private final Type type;

    private final boolean useBalance;
}


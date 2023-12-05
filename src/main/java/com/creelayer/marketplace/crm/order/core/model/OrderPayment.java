package com.creelayer.marketplace.crm.order.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderPayment {

    public enum Type {
        NONE, CASH, CARD, TRANSFER
    }

    private Type type;

    private boolean useBalance;
}


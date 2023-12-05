package com.creelayer.marketplace.crm.order.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderPayout {

    private long amount;

    private String description;
}

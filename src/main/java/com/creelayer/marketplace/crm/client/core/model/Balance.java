package com.creelayer.marketplace.crm.client.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Balance {

    public enum Currency {
        USD, UAH
    }

    private UUID uuid;

    private Currency currency;

    private long amount;
}

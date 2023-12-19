package com.creelayer.marketplace.crm.client.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BalanceDetailQuery {

    private UUID client;

    private boolean create;

    public BalanceDetailQuery(UUID client) {
        this.client = client;
    }
}

package com.creelayer.marketplace.crm.client.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BalanceResolveCommand {

    private UUID client;

    private boolean create;

    public BalanceResolveCommand(UUID client) {
        this.client = client;
    }
}

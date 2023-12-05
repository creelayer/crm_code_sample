package com.creelayer.marketplace.crm.client.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientDetailCommand {

    private final UUID client;

    @Getter
    private boolean withBalance;

}

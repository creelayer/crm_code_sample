package com.creelayer.marketplace.crm.client.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateClientCommand {

    private final UUID uuid;

    public String email;

    public String name;
}

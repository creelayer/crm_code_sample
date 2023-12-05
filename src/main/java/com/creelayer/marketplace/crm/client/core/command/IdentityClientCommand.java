package com.creelayer.marketplace.crm.client.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class IdentityClientCommand {

    private final UUID uuid;

    private final String phone;

    private String email;

    private String name;

}

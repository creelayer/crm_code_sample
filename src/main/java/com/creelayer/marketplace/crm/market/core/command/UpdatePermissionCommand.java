package com.creelayer.marketplace.crm.market.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdatePermissionCommand {

    private UUID manager;

    private List<String> scopes;
}

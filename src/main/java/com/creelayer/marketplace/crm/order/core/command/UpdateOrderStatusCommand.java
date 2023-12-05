package com.creelayer.marketplace.crm.order.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateOrderStatusCommand {

    private UUID uuid;

    private String status;

    private String comment;
}

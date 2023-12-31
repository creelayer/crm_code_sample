package com.creelayer.marketplace.crm.order.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateOrderItemCountCommand {

    private UUID order;

    private String sku;

    private int count;
}

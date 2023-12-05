package com.creelayer.marketplace.crm.market.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateMarketCommand {

    private UUID account;

    private String name;

    private String url;

    private String phone;

    private String email;
}

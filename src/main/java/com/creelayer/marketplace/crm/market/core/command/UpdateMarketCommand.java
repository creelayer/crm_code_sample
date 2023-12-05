package com.creelayer.marketplace.crm.market.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateMarketCommand {

    private UUID market;

    private String url;

    private String phone;

    private String email;
}

package com.creelayer.marketplace.crm.market.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateManagerCommand {

    private UUID account;

    private UUID market;
}

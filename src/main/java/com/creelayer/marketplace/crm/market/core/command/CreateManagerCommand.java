package com.creelayer.marketplace.crm.market.core.command;

import java.util.UUID;

public record CreateManagerCommand(UUID market, UUID account) {

}

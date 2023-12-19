package com.creelayer.marketplace.crm.promo.core.command;

import java.util.UUID;

public record CreatePromoGroupCommand(UUID realm, String name) {
}

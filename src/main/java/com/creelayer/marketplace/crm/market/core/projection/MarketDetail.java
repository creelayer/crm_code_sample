package com.creelayer.marketplace.crm.market.core.projection;

import java.util.UUID;

public interface MarketDetail {
    UUID getUuid();

    String getName();

    Account getAccount();

    interface Account {
        UUID getUuid();
    }

}

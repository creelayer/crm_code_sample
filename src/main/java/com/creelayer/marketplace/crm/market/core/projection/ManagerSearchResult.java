package com.creelayer.marketplace.crm.market.core.projection;

import java.util.UUID;

public interface ManagerSearchResult {

    UUID getUuid();

    String getStatus();

    Market getMarket();

    Account getAccount();

    default boolean isOwner(){
        return getAccount().getUuid().equals(getMarket().getAccount().getUuid());
    }

    interface Market {
        Account getAccount();
    }

    interface Account {
        UUID getUuid();

        String getEmail();
    }
}

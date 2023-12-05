package com.creelayer.marketplace.crm.market.core.projection;

import java.util.UUID;

public interface ManagerDetail {

    UUID getUuid();

    String getStatus();

    Market getMarket();

    Account getAccount();

    default boolean isActive() {
        return getStatus().equals("ACTIVE") && getMarket().isActive();
    }

    default boolean isOwner() {
        return getAccount().getUuid().equals(getMarket().getAccount().getUuid());
    }

    interface Market {
        Account getAccount();

        String getStatus();

        default boolean isActive() {
            return getStatus().equals("ACTIVE");
        }
    }

    interface Account {
        UUID getUuid();

        String getEmail();
    }
}

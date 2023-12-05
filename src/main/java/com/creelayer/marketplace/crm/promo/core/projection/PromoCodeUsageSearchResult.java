package com.creelayer.marketplace.crm.promo.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromoCodeUsageSearchResult {
    UUID getUuid();

    Client getClient();

    Code getCode();

    LocalDateTime getCreatedAt();

    interface Client {
        UUID getUuid();
    }

    interface Code {

        UUID getUuid();

        String getCode();

        String getType();

        int getDiscount();
    }
}

package com.creelayer.marketplace.crm.promo.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromoCodeSearchResult {

    UUID getUuid();

    PromoActionSearchResult.Group getGroup();

    String getName();

    String getCode();

    String getStatus();

    String getType();

    long getDiscount();

    LocalDateTime getExpiredAt();

    LocalDateTime getCreatedAt();

    interface Group {
        String getName();
    }
}

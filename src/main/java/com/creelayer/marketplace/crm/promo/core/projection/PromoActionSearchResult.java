package com.creelayer.marketplace.crm.promo.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromoActionSearchResult {

    UUID getUuid();

    String getName();

    Group getGroup();

    LocalDateTime getExpiredAt();

    LocalDateTime getCreatedAt();


    interface Group {
        String getName();
    }
}

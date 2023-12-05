package com.creelayer.marketplace.crm.promo.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromoGroupSearchResult {
    UUID getUuid();
    String getName();
    String getStatus();
    LocalDateTime getCreatedAt();
}

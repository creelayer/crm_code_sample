package com.creelayer.marketplace.crm.order.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderSearchResult {
    UUID getUuid();

    long getCode();

    String getStatus();

    Contact getContact();

    LocalDateTime getCreatedAt();

    interface Contact {
        String getName();

        String getPhone();

        String getEmail();
    }
}

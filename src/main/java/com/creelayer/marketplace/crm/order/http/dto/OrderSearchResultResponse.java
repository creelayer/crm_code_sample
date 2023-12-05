package com.creelayer.marketplace.crm.order.http.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderSearchResultResponse {
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

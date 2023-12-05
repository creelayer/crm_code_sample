package com.creelayer.marketplace.crm.client.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ClientSearchResult {

    UUID getUuid();

    String getName();

    String getPhone();

    LocalDateTime getCreatedAt();
}

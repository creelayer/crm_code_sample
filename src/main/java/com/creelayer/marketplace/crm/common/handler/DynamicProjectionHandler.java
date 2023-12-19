package com.creelayer.marketplace.crm.common.handler;

import java.util.Optional;
import java.util.UUID;

public interface DynamicProjectionHandler {
    <R> Optional<R> findByUuid(UUID id, Class<R> type);
}

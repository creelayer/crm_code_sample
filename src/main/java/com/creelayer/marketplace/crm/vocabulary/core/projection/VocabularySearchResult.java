package com.creelayer.marketplace.crm.vocabulary.core.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface VocabularySearchResult {

    UUID getUuid();

    String getName();

    String getAlias();

    LocalDateTime getCreatedAt();

}

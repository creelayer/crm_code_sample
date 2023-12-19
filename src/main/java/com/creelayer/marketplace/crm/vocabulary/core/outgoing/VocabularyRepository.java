package com.creelayer.marketplace.crm.vocabulary.core.outgoing;

import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;

import java.util.Optional;
import java.util.UUID;

public interface VocabularyRepository extends DynamicProjectionHandler {

    Optional<Vocabulary> findById(UUID id);

    Vocabulary save(Vocabulary vocabulary);

    void delete(Vocabulary vocabulary);
}

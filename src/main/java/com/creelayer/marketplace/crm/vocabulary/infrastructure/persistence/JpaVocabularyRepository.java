package com.creelayer.marketplace.crm.vocabulary.infrastructure.persistence;

import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import com.creelayer.marketplace.crm.vocabulary.core.outgoing.VocabularyRepository;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularyView;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularyViewQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaVocabularyRepository extends JpaRepository<Vocabulary, UUID>, VocabularyRepository, QueryHandler<VocabularyViewQuery, Optional<VocabularyView>> {

    @Override
    @Query("SELECT v FROM Vocabulary v WHERE v.realm.uuid = :#{#query.realm} AND v.alias = :#{#query.alias}")
    Optional<VocabularyView> ask(VocabularyViewQuery query);
}

package com.creelayer.marketplace.crm.vocabulary.infrastructure.persistence;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import com.creelayer.marketplace.crm.vocabulary.core.outgoing.VocabularyRepository;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularyView;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularyViewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaVocabularyRepository extends JpaRepository<Vocabulary, UUID>, JpaSpecificationProjectionExecutor<Vocabulary>, VocabularyRepository {

    @Query("SELECT v FROM Vocabulary v WHERE v.realm.uuid = :#{#query.realm} AND v.alias = :#{#query.alias}")
    Optional<VocabularyView> viewByQuery(VocabularyViewQuery query);

    default <R> Page<R> search(RealmIdentity realm, VocabularySearchQuery query, Pageable pageable, Class<R> rClass){
        return findAll(new VocabularySearchSpecification(realm, query), pageable, rClass);
    }
}

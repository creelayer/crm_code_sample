package com.creelayer.marketplace.crm.vocabulary.infrastructure.persistence;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.reaml.Realm;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularySearchResult;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaVocabularySearch extends
        org.springframework.data.repository.Repository<Vocabulary, UUID>,
        JpaSpecificationProjectionExecutor<Vocabulary>,
        QueryHandler<VocabularySearchQuery, Page<VocabularySearchResult>>
{


    @Override
    default  Page<VocabularySearchResult> ask(VocabularySearchQuery query){
        return findAll(
                new VocabularySearchSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                VocabularySearchResult.class
        );
    }
}

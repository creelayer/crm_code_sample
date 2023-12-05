package com.creelayer.marketplace.crm.vocabulary.core.incoming;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularyView;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularyViewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VocabularySearch {

//    Optional<VocabularyItemView> fetchItem(VocabularyItemFetchQuery query);

    Optional<VocabularyView> viewByQuery(VocabularyViewQuery query);


    <R> Page<R> search(RealmIdentity realm, VocabularySearchQuery query, Pageable pageable, Class<R> rClass);
}

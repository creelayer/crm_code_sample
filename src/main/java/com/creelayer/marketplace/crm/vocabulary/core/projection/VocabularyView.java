package com.creelayer.marketplace.crm.vocabulary.core.projection;

import java.util.Set;

public interface VocabularyView {

    String getUuid();

    String getName();

    String getAlias();

    Set<VocabularyItemView> getItems();

}

package com.creelayer.marketplace.crm.vocabulary.core.projection;


import java.time.LocalDateTime;
import java.util.Set;

public interface VocabularyItemView {

    String getName();

    String getAlias();

    boolean getIsEditable();

    boolean getIsDeletable();

    boolean getIsForward();

    Integer getWeight();

    Set<String> getForwards();

    LocalDateTime getCreatedAt();

}

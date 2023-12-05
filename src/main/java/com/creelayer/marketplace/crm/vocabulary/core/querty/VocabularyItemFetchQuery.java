package com.creelayer.marketplace.crm.vocabulary.core.querty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class VocabularyItemFetchQuery {

    private UUID vocabulary;

    private String alias;
}

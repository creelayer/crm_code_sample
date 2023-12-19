package com.creelayer.marketplace.crm.vocabulary.core.querty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class VocabularySearchQuery {

    private UUID realm;

    private String search;

    private int page;

    private int size = 50;
}

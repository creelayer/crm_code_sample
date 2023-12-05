package com.creelayer.marketplace.crm.vocabulary.core.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateVocabularyItemCommand {

    private final UUID vocabulary;

    private final String alias;

    private final String name;

    @Setter
    private boolean isForward;

    @Setter
    private Set<String> forwards;

    @Setter
    private Integer weight;

}

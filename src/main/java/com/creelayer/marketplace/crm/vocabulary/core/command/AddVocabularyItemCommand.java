package com.creelayer.marketplace.crm.vocabulary.core.command;


import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AddVocabularyItemCommand {


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

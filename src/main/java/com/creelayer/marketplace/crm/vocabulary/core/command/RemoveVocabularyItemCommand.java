package com.creelayer.marketplace.crm.vocabulary.core.command;


import java.util.UUID;

public record RemoveVocabularyItemCommand(UUID vocabulary, String alias) {

}

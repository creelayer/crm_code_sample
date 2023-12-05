package com.creelayer.marketplace.crm.vocabulary.core.incoming;

import com.creelayer.marketplace.crm.vocabulary.core.command.AddVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.command.RemoveVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.command.UpdateVocabularyItemCommand;

public interface VocabularyManage {
    void addItem(AddVocabularyItemCommand command);

    void updateItem(UpdateVocabularyItemCommand command);

    void removeItem(RemoveVocabularyItemCommand command);
}

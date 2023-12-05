package com.creelayer.marketplace.crm.vocabulary.core;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.vocabulary.core.command.AddVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.command.RemoveVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.command.UpdateVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.incoming.VocabularyManage;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import com.creelayer.marketplace.crm.vocabulary.core.model.VocabularyItem;
import com.creelayer.marketplace.crm.vocabulary.core.outgoing.VocabularyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VocabularyService implements VocabularyManage {

    private final VocabularyRepository vocabularyRepository;

    public void addItem(AddVocabularyItemCommand command) {

        Vocabulary vocabulary = vocabularyRepository.findById(command.getVocabulary())
                .orElseThrow(() -> new NotFoundException("Vocabulary not found"));

        VocabularyItem item = new VocabularyItem(command.getAlias(), command.getName(), command.getWeight(), command.isForward(), command.getForwards());

        vocabulary.putItem(item);
        vocabularyRepository.save(vocabulary);
    }

    public void updateItem(UpdateVocabularyItemCommand command) {

        Vocabulary vocabulary = vocabularyRepository.findById(command.getVocabulary())
                .orElseThrow(() -> new NotFoundException("Vocabulary not found"));

        VocabularyItem item = new VocabularyItem(command.getAlias(), command.getName(), command.getWeight(), command.isForward(), command.getForwards());
        vocabulary.putItem(item);

        vocabularyRepository.save(vocabulary);
    }

    public void removeItem(RemoveVocabularyItemCommand command) {

        Vocabulary vocabulary = vocabularyRepository.findById(command.vocabulary())
                .orElseThrow(() -> new NotFoundException("Vocabulary not found"));

        vocabulary.removeItem(command.alias());
        vocabularyRepository.save(vocabulary);
    }
}

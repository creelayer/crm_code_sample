package com.creelayer.marketplace.crm.vocabulary.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.vocabulary.core.command.RemoveVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.outgoing.VocabularyRepository;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularySearchResult;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularyView;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularyViewQuery;
import com.creelayer.marketplace.crm.vocabulary.http.dto.CreateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.dto.UpdateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.mapper.VocabularyMapper;
import com.creelayer.marketplace.crm.vocabulary.core.VocabularyService;
import com.creelayer.marketplace.crm.vocabulary.http.dto.VocabularySearchFilter;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("vocabulary")
public class VocabularyController {

    private final VocabularyService vocabularyService;

    private VocabularyRepository vocabularyRepository;

    private VocabularyMapper mapper;

    @GetMapping("")
    public Page<VocabularySearchResult> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid VocabularySearchFilter filter,
            @PageableDefault(size = 50) Pageable pageable
    ) {
        return vocabularyRepository.search(realm, mapper.map(filter), pageable, VocabularySearchResult.class);
    }

    @GetMapping("{vocabulary}")
    public VocabularyView view(@PathVariable Vocabulary vocabulary) {
        return vocabularyRepository.viewByQuery(new VocabularyViewQuery(vocabulary.getRealm().uuid, vocabulary.getAlias()))
                .orElseThrow(() -> new EntityNotFoundException("Vocabulary not found"));
    }

    @GetMapping("/alias:{alias}")
    public VocabularyView view(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String alias) {
        return vocabularyRepository.viewByQuery(new VocabularyViewQuery(realm.getUuid(), alias))
                .orElseThrow(() -> new EntityNotFoundException("Vocabulary not found"));
    }

    @PreAuthorize("hasPermission(#vocabulary.realm, 'vocabulary_manage')")
    @PostMapping("{vocabulary}/item")
    public void add(@PathVariable Vocabulary vocabulary, @Valid @RequestBody CreateVocabularyItemRequest request) {
        vocabularyService.addItem(mapper.map(vocabulary.getUuid(), request));
    }

    @PreAuthorize("hasPermission(#vocabulary.realm, 'vocabulary_manage')")
    @PutMapping("{vocabulary}/item")
    public void update(@PathVariable Vocabulary vocabulary, @Valid @RequestBody UpdateVocabularyItemRequest request) {
        vocabularyService.updateItem(mapper.map(vocabulary.getUuid(), request));
    }

    @PreAuthorize("hasPermission(#vocabulary.realm, 'vocabulary_manage')")
    @DeleteMapping("{vocabulary}/item/{alias}")
    public void removeItem(@PathVariable Vocabulary vocabulary, @PathVariable String alias) {
        vocabularyService.removeItem(new RemoveVocabularyItemCommand(vocabulary.getUuid(), alias));
    }
}

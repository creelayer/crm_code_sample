package com.creelayer.marketplace.crm.vocabulary.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.vocabulary.core.command.RemoveVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.outgoing.VocabularyRepository;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularySearchResult;
import com.creelayer.marketplace.crm.vocabulary.core.projection.VocabularyView;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularyViewQuery;
import com.creelayer.marketplace.crm.vocabulary.http.dto.CreateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.dto.UpdateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.mapper.VocabularyMapper;
import com.creelayer.marketplace.crm.vocabulary.core.VocabularyService;
import com.creelayer.marketplace.crm.vocabulary.http.dto.VocabularySearchFilter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("vocabulary")
public class VocabularyController {

    private final QueryHandler<VocabularySearchQuery, Page<VocabularySearchResult>> search;

    private final QueryHandler<VocabularyViewQuery, Optional<VocabularyView>> queryHandler;

    private final VocabularyService vocabularyService;

    private VocabularyRepository vocabularyRepository;

    private VocabularyMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'vocabulary_manage')")
    @GetMapping("")
    public Page<VocabularySearchResult> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid VocabularySearchFilter filter
    ) {
        return search.ask(mapper.map(realm.getUuid(), filter));
    }

    @GetMapping("{vocabulary}")
    public VocabularyView view(@PathVariable UUID vocabulary) {
        return vocabularyRepository.findByUuid(vocabulary, VocabularyView.class)
                .orElseThrow(() -> new EntityNotFoundException("Vocabulary not found"));
    }

    @GetMapping("/alias:{alias}")
    public VocabularyView view(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String alias) {
        return queryHandler.ask(new VocabularyViewQuery(realm.getUuid(), alias))
                .orElseThrow(() -> new EntityNotFoundException("Vocabulary not found"));
    }

    @PreAuthorize("hasPermission(#vocabulary, 'Vocabulary', 'vocabulary_manage')")
    @PostMapping("{vocabulary}/item")
    public void add(@PathVariable UUID vocabulary, @Valid @RequestBody CreateVocabularyItemRequest request) {
        vocabularyService.addItem(mapper.map(vocabulary, request));
    }

    @PreAuthorize("hasPermission(#vocabulary, 'Vocabulary', 'vocabulary_manage')")
    @PutMapping("{vocabulary}/item")
    public void update(@PathVariable UUID vocabulary, @Valid @RequestBody UpdateVocabularyItemRequest request) {
        vocabularyService.updateItem(mapper.map(vocabulary, request));
    }

    @PreAuthorize("hasPermission(#vocabulary, 'Vocabulary', 'vocabulary_manage')")
    @DeleteMapping("{vocabulary}/item/{alias}")
    public void removeItem(@PathVariable UUID vocabulary, @PathVariable String alias) {
        vocabularyService.removeItem(new RemoveVocabularyItemCommand(vocabulary, alias));
    }
}

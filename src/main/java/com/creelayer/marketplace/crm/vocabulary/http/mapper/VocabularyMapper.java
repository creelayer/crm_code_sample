package com.creelayer.marketplace.crm.vocabulary.http.mapper;

import com.creelayer.marketplace.crm.vocabulary.core.command.AddVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.command.UpdateVocabularyItemCommand;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import com.creelayer.marketplace.crm.vocabulary.http.dto.CreateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.dto.UpdateVocabularyItemRequest;
import com.creelayer.marketplace.crm.vocabulary.http.dto.VocabularySearchFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VocabularyMapper {

    VocabularySearchQuery map(UUID realm, VocabularySearchFilter filter);

    @Mapping(target = "vocabulary", source = "uuid")
    @Mapping(target = "forward", source = "request.isForward")
    AddVocabularyItemCommand map(UUID uuid, CreateVocabularyItemRequest request);

    @Mapping(target = "vocabulary", source = "uuid")
    @Mapping(target = "forward", source = "request.isForward")
    UpdateVocabularyItemCommand map(UUID uuid, UpdateVocabularyItemRequest request);
}

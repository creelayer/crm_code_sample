package com.creelayer.marketplace.crm.vocabulary.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class VocabularySearchFilter {

    @Length(max = 20)
    public String search;
}

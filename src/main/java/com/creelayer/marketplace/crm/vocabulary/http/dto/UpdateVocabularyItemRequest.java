package com.creelayer.marketplace.crm.vocabulary.http.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdateVocabularyItemRequest {

    @Length(max = 255)
    @NotEmpty(message = "Alias can't be null")
    @Pattern(regexp = "^[0-9a-zA-Z.\\-:_]+$")
    public String alias;

    @Length(max = 255)
    @NotEmpty(message = "Name can't be null")
    public String name;

    public boolean isForward;

    public List<String> forwards = new ArrayList<>();

    public Integer weight;
}

package com.creelayer.marketplace.crm.vocabulary.http.dto;

import com.creelayer.activity.data.ActivityLogIdentity;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;

import java.util.UUID;

@ActivityLogIdentity(value = "uuid", type = Vocabulary.class)
public class VocabularyViewResponse {

    public UUID uuid;

    public String name;

    public String alias;

}

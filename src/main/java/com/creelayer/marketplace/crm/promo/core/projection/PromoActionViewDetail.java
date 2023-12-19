package com.creelayer.marketplace.crm.promo.core.projection;

import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface PromoActionViewDetail {

    UUID getUuid();

    PromoGroup getGroup();

    String getName();

    String getType();

    String getSubtype();

    LocalDateTime getExpiredAt();

    boolean getTimer();

    JsonNode getFeatures();

    List<Condition> getConditions();

    Map<String, String> getDescription();

    Map<String, String> getLocales();

    LocalDateTime getDeletedAt();
}

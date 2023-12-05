package com.creelayer.marketplace.crm.loyalty.core.projection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface LoyaltySettingView {

    String getType();

    JsonNode getData();

    default <T> T getData(Class<T> tClass) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.treeToValue(getData(), tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

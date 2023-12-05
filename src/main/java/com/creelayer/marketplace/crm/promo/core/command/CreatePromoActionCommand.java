package com.creelayer.marketplace.crm.promo.core.command;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreatePromoActionCommand {

    public enum Type {
        SIMPLE, DELIVERY, RELATED
    }

    public enum Subtype {
        ACTION, DELIVERY, GIFT
    }

    private UUID group;

    private String name;

    private Map<String, String> locales;

    private Type type;

    private Subtype subtype;

    private boolean timer;

    private JsonNode features;

    private LocalDateTime expiredAt;

    private Map<String, String> description;

}

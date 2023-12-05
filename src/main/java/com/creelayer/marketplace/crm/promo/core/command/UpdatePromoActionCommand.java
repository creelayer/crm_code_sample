package com.creelayer.marketplace.crm.promo.core.command;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Getter
@AllArgsConstructor
public class UpdatePromoActionCommand {

    private UUID action;

    private String name;

    private Map<String, String> locales;

    private boolean timer;

    private JsonNode features;

    private LocalDateTime expiredAt;

    private Map<String,  String> description;
}

package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ManagePromoConditionCommand {

    private UUID uuid;

    private List<Condition> conditions;

    @Getter
    @AllArgsConstructor
    public static class Condition {

        private String path;


        private String labels;


        private Long priceFrom;


        private Long priceTo;

        private List<String> skus;

        private boolean invert;
    }

}

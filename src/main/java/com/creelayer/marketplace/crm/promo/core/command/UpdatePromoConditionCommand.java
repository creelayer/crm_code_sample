package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdatePromoConditionCommand {

    private String path;

    private String labels;

    private Long priceFrom;

    private Long priceTo;

    private List<String> skus;

    private boolean invert;
}

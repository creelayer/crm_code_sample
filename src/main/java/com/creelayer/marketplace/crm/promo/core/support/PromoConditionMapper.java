package com.creelayer.marketplace.crm.promo.core.support;

import com.creelayer.marketplace.crm.promo.core.command.Condition;
import com.creelayer.marketplace.crm.promo.core.model.PromoCondition;

import java.util.List;
import java.util.stream.Collectors;

public class PromoConditionMapper {

    public PromoCondition map(Condition data) {

        PromoCondition condition = new PromoCondition(data.getPath(), data.getLabels());
        condition.setSkus(data.getSkus());
        condition.setInvert(data.isInvert());
        condition.setPriceFrom(data.getPriceFrom());
        condition.setPriceTo(data.getPriceTo());

        return condition;
    }

    public List<PromoCondition> map(List<Condition> conditions) {
        return conditions.stream().map(this::map).collect(Collectors.toList());
    }

}

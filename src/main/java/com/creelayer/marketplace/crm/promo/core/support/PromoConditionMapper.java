package com.creelayer.marketplace.crm.promo.core.support;

import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoCondition;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PromoConditionMapper {

    public PromoCondition map(ManagePromoConditionCommand.Condition data) {

        PromoCondition condition = new PromoCondition(data.getPath(), data.getLabels());
        condition.setSkus(data.getSkus());
        condition.setInvert(data.isInvert());
        condition.setPriceFrom(data.getPriceFrom());
        condition.setPriceTo(data.getPriceTo());

        return condition;
    }

    public List<PromoCondition> map(List<ManagePromoConditionCommand.Condition> conditions) {
        return conditions.stream().map(this::map).collect(Collectors.toList());
    }

}

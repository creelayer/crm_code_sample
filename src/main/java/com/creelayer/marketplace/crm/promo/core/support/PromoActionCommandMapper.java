package com.creelayer.marketplace.crm.promo.core.support;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;


public class PromoActionCommandMapper extends PromoConditionMapper {

    public PromoAction map(PromoGroup group, CreatePromoActionCommand command) {

        PromoAction action = new PromoAction(
                group,
                command.getName(),
                PromoAction.Type.valueOf(command.getType().name()),
                PromoAction.Subtype.valueOf(command.getSubtype().name()),
                command.getExpiredAt(),
                command.getFeatures()
        );

        action.setTimer(command.isTimer());
        action.setDescription(command.getDescription());
        action.setLocales(command.getLocales());

        return action;
    }


    public PromoAction map(UpdatePromoActionCommand command, PromoAction action) {
        action.setExpiredAt(command.getExpiredAt());
        action.setTimer(command.isTimer());
        action.setName(command.getName());
        action.setDescription(command.getDescription());
        action.setLocales(command.getLocales());
        action.setFeatures(command.getFeatures());
        return action;
    }

}

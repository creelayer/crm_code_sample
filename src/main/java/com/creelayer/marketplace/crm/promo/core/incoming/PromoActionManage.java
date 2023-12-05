package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;

import java.util.UUID;

public interface PromoActionManage extends PromoConditionSupport<PromoAction> {
    PromoAction create(CreatePromoActionCommand command);

    PromoAction update(UpdatePromoActionCommand command);

    void remove(UUID uuid);
}

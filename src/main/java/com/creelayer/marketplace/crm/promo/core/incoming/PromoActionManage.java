package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;

import java.util.UUID;

public interface PromoActionManage extends PromoConditionSupport<PromoAction> {
    UUID create(CreatePromoActionCommand command);

    void update(UpdatePromoActionCommand command);

    void remove(UUID uuid);
}

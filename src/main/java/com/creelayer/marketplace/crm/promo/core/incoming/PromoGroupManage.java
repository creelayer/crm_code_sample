package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.model.Realm;

import java.util.UUID;

public interface PromoGroupManage {
    UUID create(CreatePromoGroupCommand command);

    void update(UpdatePromoGroupCommand command);

    void remove(UUID uuid);
}

package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.command.UsePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeDetail;

public interface PromoCodeUsage {

    PromoCodeDetail review(Realm realm, String code);

    void use(UsePromoCodeCommand command);

}

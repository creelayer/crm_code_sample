package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.command.UsePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.Realm;

public interface PromoCodeUsage {

    PromoCode getCode(Realm realm, String code);

    void useCode(Realm realm, UsePromoCodeCommand command);

}

package com.creelayer.marketplace.crm.order.infrastucture.provider;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsage;
import com.creelayer.marketplace.crm.order.core.model.OrderPromoCode;
import com.creelayer.marketplace.crm.order.core.CheckoutPromoCodeProvider;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CheckoutCheckoutPromoCodeProvider implements CheckoutPromoCodeProvider {

    private final PromoCodeUsage promoCodeUsage;

    private final RealmIdentityProvider realmProvider;

    @Override
    public OrderPromoCode resolve(String code) {
        RealmIdentity identity = realmProvider.resolve();
        PromoCode promoCode = promoCodeUsage.getCode(new Realm(identity.getUuid()), code);
        return new OrderPromoCode(
                OrderPromoCode.TYPE.valueOf(promoCode.getType().name()),
                promoCode.getCode(),
                promoCode.getDiscount()
        );
    }
}

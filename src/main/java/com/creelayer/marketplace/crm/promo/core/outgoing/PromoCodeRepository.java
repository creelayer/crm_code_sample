package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.Realm;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PromoCodeRepository extends DynamicProjectionHandler {

    Optional<PromoCode> findById(UUID uuid);

    Optional<PromoCode> findPromoCode(Realm realm, String code);

    boolean existsPromoCode(Realm realm, String code);

    PromoCode save(PromoCode promoCode);

    void save(Collection<PromoCode> entities);

    void delete(PromoCode code);

}

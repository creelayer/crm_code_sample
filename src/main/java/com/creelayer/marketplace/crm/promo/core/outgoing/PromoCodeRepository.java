package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeAnalytic;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeSearch;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.Realm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromoCodeRepository extends PromoCodeSearch, PromoCodeAnalytic {

    Optional<PromoCode> findById(UUID uuid);

    boolean existsPromoCode(Realm realm, String code);

    Optional<PromoCode> findPromoCode(Realm realm, String code);

    PromoCode save(PromoCode promoCode);

    void save(List<PromoCode> entities);

    void delete(PromoCode code);

}

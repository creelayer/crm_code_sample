package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoActionSearch;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;

import java.util.Optional;
import java.util.UUID;

public interface PromoActionRepository extends PromoActionSearch {

    Optional<PromoAction> findById(UUID uuid);

    PromoAction save(PromoAction group);

    void delete(PromoAction group);

}

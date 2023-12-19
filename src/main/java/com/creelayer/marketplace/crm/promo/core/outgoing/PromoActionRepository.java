package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;

import java.util.Optional;
import java.util.UUID;

public interface PromoActionRepository extends DynamicProjectionHandler {

    Optional<PromoAction> findById(UUID uuid);

    PromoAction save(PromoAction group);

    void delete(PromoAction group);

}

package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupSearch;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;

import java.util.Optional;
import java.util.UUID;

public interface PromoGroupRepository extends PromoGroupSearch {

    Optional<PromoGroup> findById(UUID uuid);

    PromoGroup save(PromoGroup group);

    void delete(PromoGroup group);

}

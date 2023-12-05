package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsageSearch;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeUses;

public interface PromoCodeUsagesRepository extends PromoCodeUsageSearch {

    long countUsages(PromoCode code);

    PromoCodeUses save(PromoCodeUses usage);

}

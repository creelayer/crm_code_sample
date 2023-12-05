package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoGroupSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoGroupSearch {

    Page<PromoGroupSearchResult> search(Realm realm, PromoGroupSearchQuery query, Pageable pageable);

}

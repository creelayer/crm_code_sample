package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoActionSearch {

    Page<PromoActionSearchResult> search(Realm realm, PromoActionSearchQuery query, Pageable pageable);

}

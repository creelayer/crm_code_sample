package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchResult;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeUsageSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeUsageSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoCodeUsageSearch {

    Page<PromoCodeUsageSearchResult> search(Realm realm, PromoCodeUsageSearchQuery query, Pageable pageable);

}

package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoCodeSearch {
    <T> Page<T> search(Realm realm, PromoCodeSearchQuery query, Pageable pageable, Class<T> tClass);
}

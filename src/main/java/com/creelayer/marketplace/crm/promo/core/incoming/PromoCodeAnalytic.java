package com.creelayer.marketplace.crm.promo.core.incoming;

import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeClientStatistic;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeAnalyticsQuery;

public interface PromoCodeAnalytic {
    PromoCodeClientStatistic clientStatistic(PromoCodeAnalyticsQuery query);
}

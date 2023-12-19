package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeAnalytic;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeClientStatistic;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeAnalyticsQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoCodeAnalytics extends org.springframework.data.repository.Repository<PromoCode, UUID>, PromoCodeAnalytic {

    @Override
    @Query(value = "SELECT count(case when c.expired_at > now() AND u.code_uuid IS NULL AND c.status = 'ACTIVE' then true end) as active, " +
            "count(u.code_uuid) as used, " +
            "count(case when c.expired_at < now() AND u.code_uuid IS NULL then true end) as archived " +
            "FROM promo_code c " +
            "LEFT JOIN promo_code_uses u ON u.code_uuid = c.uuid " +
            "WHERE c.owner_uuid = :#{#query.client} AND c.deleted_at IS NULL ", nativeQuery = true)
    PromoCodeClientStatistic clientStatistic(PromoCodeAnalyticsQuery query);
}

package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeClientStatistic;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeAnalyticsQuery;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPromoCodeRepository extends JpaRepository<PromoCode, UUID>, JpaSpecificationExecutor<PromoCode>, JpaSpecificationProjectionExecutor<PromoCode> , PromoCodeRepository {

    @Query("SELECT c FROM PromoCode c JOIN c.group g WHERE g.realm = :realm AND c.code = :code AND c.deletedAt IS NULL ")
    Optional<PromoCode> findPromoCode(Realm realm, String code);

    @Query("SELECT case when count(c)> 0 then true else false end FROM PromoCode c JOIN c.group g WHERE g.realm = :realm AND c.code = :code AND c.deletedAt IS NULL ")
    boolean existsPromoCode(Realm realm, String code);

    @Query(value = "SELECT count(case when c.expired_at > now() AND u.code_uuid IS NULL AND c.status = 'ACTIVE' then true end) as active, " +
            "count(u.code_uuid) as used, " +
            "count(case when c.expired_at < now() AND u.code_uuid IS NULL then true end) as archived " +
            "FROM promo_code c " +
            "LEFT JOIN promo_code_uses u ON u.code_uuid = c.uuid " +
            "WHERE c.owner_uuid = :#{#query.client} AND c.deleted_at IS NULL ", nativeQuery = true)
    PromoCodeClientStatistic clientStatistic(PromoCodeAnalyticsQuery query);

    @Override
    default void save(List<PromoCode> entities){
        saveAll(entities);
    }

    @Override
    default <T> Page<T> search(Realm realm, PromoCodeSearchQuery query, Pageable pageable, Class<T> tClass){
        return findAll(new PromoCodeSpecification(realm, query), pageable, tClass);
    }
}

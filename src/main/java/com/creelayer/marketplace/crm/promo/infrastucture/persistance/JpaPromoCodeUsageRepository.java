package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeUsagesRepository;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeUses;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeUsageSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeUsageSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoCodeUsageRepository extends JpaRepository<PromoCodeUses, UUID>, JpaSpecificationProjectionExecutor<PromoCodeUses> , PromoCodeUsagesRepository {

    @Query("SELECT COUNT(u) FROM PromoCodeUses u JOIN u.code c WHERE u.code = :code AND c.deletedAt IS NULL ")
    long countUsages(PromoCode code);

    @Override
    default Page<PromoCodeUsageSearchResult> search(Realm realm, PromoCodeUsageSearchQuery query, Pageable pageable){
        return findAll(new PromoCodeUsageSpecification(realm, query), pageable, PromoCodeUsageSearchResult.class);
    }
}

package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoActionRepository;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoActionRepository extends JpaRepository<PromoAction, UUID>, JpaSpecificationProjectionExecutor<PromoAction>, PromoActionRepository {

    @Override
    default Page<PromoActionSearchResult> search(Realm realm, PromoActionSearchQuery query, Pageable pageable){
        return findAll(new PromoActionSpecification(realm, query), pageable, PromoActionSearchResult.class);
    }
}

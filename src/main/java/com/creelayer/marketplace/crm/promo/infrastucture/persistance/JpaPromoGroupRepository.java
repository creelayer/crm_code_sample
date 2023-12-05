package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoGroupSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoGroupRepository extends JpaRepository<PromoGroup, UUID>, JpaSpecificationProjectionExecutor<PromoGroup>, PromoGroupRepository {

    @Override
    default Page<PromoGroupSearchResult> search(Realm realm, PromoGroupSearchQuery query, Pageable pageable){
        return findAll(new PromoGroupSpecification(realm, query), pageable, PromoGroupSearchResult.class);
    }
}

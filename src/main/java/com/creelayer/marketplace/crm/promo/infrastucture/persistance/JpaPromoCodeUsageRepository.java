package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeUses;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeUsageSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeUsageSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoCodeUsageRepository extends
        org.springframework.data.repository.Repository<PromoCodeUses, UUID>,
        JpaSpecificationProjectionExecutor<PromoCodeUses> ,
        QueryHandler<PromoCodeUsageSearchQuery, Page<PromoCodeUsageSearchResult>>
{

    @Query("SELECT COUNT(u) FROM PromoCodeUses u JOIN u.code c WHERE u.code = :code AND c.deletedAt IS NULL ")
    long countUsages(PromoCode code);

    @Override
    default Page<PromoCodeUsageSearchResult> ask(PromoCodeUsageSearchQuery query){
        return findAll(
                new PromoCodeUsageSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                PromoCodeUsageSearchResult.class
        );
    }
}

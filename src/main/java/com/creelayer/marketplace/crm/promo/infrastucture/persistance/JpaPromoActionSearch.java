package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoActionSearch extends
        org.springframework.data.repository.Repository<PromoAction, UUID>,
        JpaSpecificationProjectionExecutor<PromoAction> ,
        QueryHandler<PromoActionSearchQuery, Page<PromoActionSearchResult>>
{

    @Override
    default Page<PromoActionSearchResult> ask(PromoActionSearchQuery query){
        return findAll(new PromoActionSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                PromoActionSearchResult.class
        );
    }
}

package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoCodeSearch extends
        org.springframework.data.repository.Repository<PromoCode, UUID>,
        JpaSpecificationProjectionExecutor<PromoCode> ,
        QueryHandler<PromoCodeSearchQuery, Page<PromoCodeSearchResult>>
{

    @Override
    default Page<PromoCodeSearchResult> ask(PromoCodeSearchQuery query){
        return findAll(new PromoCodeSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                PromoCodeSearchResult.class
        );
    }
}

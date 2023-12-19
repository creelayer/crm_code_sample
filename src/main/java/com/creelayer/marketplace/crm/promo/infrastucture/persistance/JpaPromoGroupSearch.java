package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoGroupSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoGroupSearch extends
        org.springframework.data.repository.Repository<PromoGroup, UUID>,
        JpaSpecificationProjectionExecutor<PromoGroup>,
        QueryHandler<PromoGroupSearchQuery, Page<PromoGroupSearchResult>>
{
    @Override
    default Page<PromoGroupSearchResult> ask(PromoGroupSearchQuery query){
        return findAll(
                new PromoGroupSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                PromoGroupSearchResult.class
        );
    }
}

package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.projection.MarketSearchResult;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaMarketSearch extends
        org.springframework.data.repository.Repository<Market, UUID>,
        JpaSpecificationProjectionExecutor<Market>,
        QueryHandler<MarketSearchQuery, Page<MarketSearchResult>> {

    @Override
    default  Page<MarketSearchResult> ask(MarketSearchQuery query) {
        return findAll(
                new MarketSearchSpecification(query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                MarketSearchResult.class);
    }

}

package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.projection.MarketDetail;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketSearch {

    Optional<MarketDetail> getDetail(UUID marketUuid);

    List<MarketDetail> findAvailable(UUID accountUuid);

    <R> Page<R> search(MarketSearchQuery query, Pageable pageable, Class<R> rClass);
}

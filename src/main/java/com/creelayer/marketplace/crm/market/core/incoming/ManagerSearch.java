package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.projection.ManagerDetail;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ManagerSearch {

    Optional<ManagerDetail> getDetail(UUID account, UUID market);

    <R> Page<R> search(ManagerSearchQuery query, Pageable pageable, Class<R> rClass);
}

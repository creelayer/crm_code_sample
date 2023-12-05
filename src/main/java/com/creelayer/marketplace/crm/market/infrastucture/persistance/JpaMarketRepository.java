package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.market.core.projection.MarketDetail;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaMarketRepository extends JpaRepository<Market, UUID>, JpaSpecificationProjectionExecutor<Market>, MarketRepository {

    @Query("SELECT m FROM Market m WHERE m.uuid = :uuid AND m.deletedAt IS NULL")
    Optional<MarketDetail> getDetail(UUID uuid);

    @Override
    @Query("SELECT m FROM Market m WHERE m.account.uuid = :accountUuid")
    List<MarketDetail> findAvailable(UUID accountUuid);

    @Override
    default <R> Page<R> search(MarketSearchQuery query, Pageable pageable, Class<R> rClass) {
        return findAll(new MarketSearchSpecification(query), pageable, rClass);
    }

}

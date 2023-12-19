package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.market.core.incoming.AccessibleMarket;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.market.core.projection.MarketShortDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMarketRepository extends JpaRepository<Market, UUID>, MarketRepository, AccessibleMarket {

    @Override
    @Query("SELECT m FROM Market m WHERE m.account.uuid = :account")
    List<MarketShortDetail> ask(UUID account);

}

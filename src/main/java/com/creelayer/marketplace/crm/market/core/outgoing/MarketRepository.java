package com.creelayer.marketplace.crm.market.core.outgoing;

import com.creelayer.marketplace.crm.market.core.incoming.MarketSearch;
import com.creelayer.marketplace.crm.market.core.model.Market;

import java.util.Optional;
import java.util.UUID;

public interface MarketRepository extends MarketSearch {

    Optional<Market> findById(UUID uuid);

    Market save(Market market);

    void delete(Market market);

}

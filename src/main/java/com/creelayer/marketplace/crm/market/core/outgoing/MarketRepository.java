package com.creelayer.marketplace.crm.market.core.outgoing;

import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;
import com.creelayer.marketplace.crm.market.core.model.Market;

import java.util.Optional;
import java.util.UUID;

public interface MarketRepository extends DynamicProjectionHandler {

    Optional<Market> findById(UUID uuid);

    Market save(Market market);

    void delete(Market market);

}

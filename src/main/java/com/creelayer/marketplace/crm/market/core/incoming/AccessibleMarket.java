package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.projection.MarketShortDetail;

import java.util.List;
import java.util.UUID;

public interface AccessibleMarket {
    List<MarketShortDetail> ask(UUID account);
}

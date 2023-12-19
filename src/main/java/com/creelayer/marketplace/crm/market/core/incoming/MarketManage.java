package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.command.CreateMarketCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;

import java.util.UUID;

public interface MarketManage {

    UUID create(CreateMarketCommand command);

    void update(UpdateMarketCommand command);

    void remove(UUID uuid);
}

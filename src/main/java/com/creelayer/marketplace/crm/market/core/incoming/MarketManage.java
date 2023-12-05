package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.command.CreateMarketCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.model.Market;

import java.util.UUID;

public interface MarketManage {


    Market create(CreateMarketCommand command);

    Market update(UpdateMarketCommand command);

    void remove(UUID uuid);
}

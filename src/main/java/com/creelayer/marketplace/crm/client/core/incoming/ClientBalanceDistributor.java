package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.command.BalanceResolveCommand;
import com.creelayer.marketplace.crm.client.core.model.Balance;

public interface ClientBalanceDistributor {

    Balance getClientBalance(BalanceResolveCommand command);
}

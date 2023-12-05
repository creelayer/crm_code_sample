package com.creelayer.marketplace.crm.client.core.outgoing;

import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.ClientBalanceKey;

public interface ClientBalanceProvider {

    ClientBalanceKey createBalanceKey(Client client);

    Balance getBalance(Client client);
}

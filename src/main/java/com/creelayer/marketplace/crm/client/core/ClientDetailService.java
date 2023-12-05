package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.command.ClientDetailCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientDetailDistributor;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.projection.ClientDetail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientDetailService implements ClientDetailDistributor {

    private final ClientIdentity clientIdentity;

    private final ClientBalanceProvider balanceProvider;

    @Override
    public ClientDetail getClientDetail(ClientDetailCommand command) {

        Client client = clientIdentity.getClient(command.getClient());

        if (command.isWithBalance()) {
            Balance balance = balanceProvider.getBalance(client);
            return new ClientDetail(client, balance);
        }

        return new ClientDetail(client);
    }
}

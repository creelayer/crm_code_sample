package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.query.BalanceDetailQuery;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.ClientBalanceKey;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientBalanceHandler implements QueryHandler<BalanceDetailQuery, Balance> {

    private final ClientBalanceProvider balanceProvider;

    private final ClientRepository clientRepository;

    @Override
    public Balance ask(BalanceDetailQuery query) {

        Client client = clientRepository.findById(query.getClient())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Balance balance = balanceProvider.getBalance(client);

        if (balance != null || !query.isCreate())
            return balance;

        if (client.getBalanceAccessKey() == null)
            createClientBalanceKey(client);

        return balanceProvider.getBalance(client);
    }

    private void createClientBalanceKey(Client client) {
        ClientBalanceKey key = balanceProvider.createBalanceKey(client);
        client.setBalanceAccessKey(key);
        clientRepository.save(client);
    }
}

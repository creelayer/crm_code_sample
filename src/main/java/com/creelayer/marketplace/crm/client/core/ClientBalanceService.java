package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.command.BalanceResolveCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientBalanceDistributor;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.ClientBalanceKey;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientBalanceService implements ClientBalanceDistributor {

    private final ClientBalanceProvider balanceProvider;

    private final ClientRepository clientRepository;

    @Override
    public Balance getClientBalance(BalanceResolveCommand command) {

        Client client = clientRepository.findById(command.getClient())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Balance balance = balanceProvider.getBalance(client);

        if (balance != null || !command.isCreate())
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

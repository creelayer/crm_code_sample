package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.client.core.query.ClientDetailQuery;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.projection.ClientDetail;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientDetailHandler implements QueryHandler<ClientDetailQuery, ClientDetail> {

    private final ClientRepository clientRepository;

    private final ClientBalanceProvider balanceProvider;

    @Override
    public ClientDetail ask(ClientDetailQuery query) {

        Client client = clientRepository.findByRealmAndPhone(new Realm(query.realm()), query.phone())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (query.withBalance()) {
            Balance balance = balanceProvider.getBalance(client);
            return new ClientDetail(client, balance);
        }

        return new ClientDetail(client);
    }
}

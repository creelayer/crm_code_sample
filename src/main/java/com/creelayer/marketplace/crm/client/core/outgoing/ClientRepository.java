package com.creelayer.marketplace.crm.client.core.outgoing;

import com.creelayer.marketplace.crm.client.core.incoming.ClientSearch;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.model.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends ClientSearch {

    Optional<Client> findById(UUID uuid);

    Optional<Client> findByRealmAndPhone(Realm realm, String phone);

    Client save(Client client);

}

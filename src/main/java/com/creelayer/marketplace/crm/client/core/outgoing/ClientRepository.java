package com.creelayer.marketplace.crm.client.core.outgoing;

import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.common.handler.DynamicProjectionHandler;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends DynamicProjectionHandler {

    Optional<Client> findById(UUID uuid);

    Optional<Client> findByRealmAndPhone(Realm realm, String phone);

    Client save(Client client);

}

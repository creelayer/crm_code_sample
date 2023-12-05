package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.command.IdentityClientCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.common.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ClientIdentityService implements ClientIdentity {

    private final ClientRepository clientRepository;

    @Override
    public Client getClient(UUID uuid) {
        return clientRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    @Override
    public Client getClient(Realm realm, String phone) {
        return clientRepository.findByRealmAndPhone(realm, phone)
                .orElseThrow(() -> new NotFoundException(("Client not found")));
    }

    @Override
    public Client identClient(IdentityClientCommand command) {

        Client client = clientRepository
                .findByRealmAndPhone(new Realm(command.getUuid()), command.getPhone())
                .orElseGet(() -> new Client(new Realm(command.getUuid()), command.getPhone()).initRegistration());

        client.setEmail(client.getEmail());
        client.setName(client.getName());

        return clientRepository.save(client);
    }
}

package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientApiCommand;
import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientManage;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientService implements ClientManage {

    private final ClientRepository clientRepository;

    @Override
    public void manage(UpdateClientApiCommand command) {
        Client client = clientRepository.findByRealmAndPhone(new Realm(command.realm()), command.phone())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        client.setEmail(command.email()).setName(command.name());
        clientRepository.save(client);
    }

    @Override
    public void manage(UpdateClientCommand command) {

        Client client = clientRepository.findById(command.client())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        client.setEmail(command.email()).setName(command.name());
        clientRepository.save(client);
    }
}

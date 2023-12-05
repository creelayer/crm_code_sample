package com.creelayer.marketplace.crm.client.core;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientManage;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientService implements ClientManage {

    private final ClientRepository clientRepository;

    @Override
    public Client manage(UpdateClientCommand command) {

        Client client = clientRepository.findById(command.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        client.setEmail(command.getEmail()).setName(command.getName());
        return clientRepository.save(client);
    }
}

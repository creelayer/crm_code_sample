package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.command.IdentityClientCommand;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;

import java.util.UUID;

public interface ClientIdentity {
    Client getClient(UUID uuid);

    Client getClient(Realm realm, String phone);

    Client identClient(IdentityClientCommand command);
}

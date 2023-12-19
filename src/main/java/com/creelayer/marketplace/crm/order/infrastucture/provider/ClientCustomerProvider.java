package com.creelayer.marketplace.crm.order.infrastucture.provider;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.ClientIdentityService;
import com.creelayer.marketplace.crm.client.core.command.IdentityClientCommand;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.order.core.model.OrderCustomer;
import com.creelayer.marketplace.crm.order.core.outgoing.CustomerProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class ClientCustomerProvider implements CustomerProvider {

    private final ClientIdentityService identityService;

    private final RealmIdentityProvider realmProvider;

    @Override
    public OrderCustomer resolve(String phone) {
        RealmIdentity identity = realmProvider.resolve();
        Client client = identityService.getClient(new Realm(identity.getUuid()), phone);
        return new OrderCustomer(
                client.getUuid(),
                client.getName(),
                client.getPhone(),
                client.getEmail()
        );
    }

    @Override
    public OrderCustomer identity(Contact contact) {
        RealmIdentity identity = realmProvider.resolve();
        Client client = identityService.identClient(
                new IdentityClientCommand(identity.getUuid(), contact.phone(), contact.email(), contact.name())
        );
        return new OrderCustomer(
                client.getUuid(),
                client.getName(),
                client.getPhone(),
                client.getEmail()
        );
    }
}

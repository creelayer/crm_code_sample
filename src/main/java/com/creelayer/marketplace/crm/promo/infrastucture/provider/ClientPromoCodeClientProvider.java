package com.creelayer.marketplace.crm.promo.infrastucture.provider;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeClientProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ClientPromoCodeClientProvider implements PromoCodeClientProvider {

    private final ClientIdentity clientIdentity;

    private final RealmIdentityProvider realmProvider;

    @Override
    public PromoCodeClient resolve(UUID uuid) {
        Client client = clientIdentity.getClient(uuid);
        return new PromoCodeClient(client.getUuid());
    }

    @Override
    public PromoCodeClient resolve(String phone) {
        RealmIdentity identity = realmProvider.resolve();
        Client client = clientIdentity.getClient(new Realm(identity.getUuid()), phone);
        return new PromoCodeClient(client.getUuid());
    }
}

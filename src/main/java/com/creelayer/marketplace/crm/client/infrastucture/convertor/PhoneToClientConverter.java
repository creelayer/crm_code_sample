package com.creelayer.marketplace.crm.client.infrastucture.convertor;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.model.Client;

import com.creelayer.marketplace.crm.client.core.model.Realm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

@RequiredArgsConstructor
public final class PhoneToClientConverter implements Converter<String, Client> {

    private final ClientIdentity clientIdentity;

    private final RealmIdentityProvider realmProvider;

    @SneakyThrows
    @Override
    public Client convert(String id) {

        if (id.matches("^[0-9]{12}$")) {
            RealmIdentity identity = realmProvider.resolve();
            return clientIdentity.getClient(new Realm(identity.getUuid()), id);
        }

        if (id.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
            return clientIdentity.getClient(UUID.fromString(id));

        return null;
    }

}

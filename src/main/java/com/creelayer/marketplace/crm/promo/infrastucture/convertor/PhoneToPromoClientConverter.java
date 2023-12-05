package com.creelayer.marketplace.crm.promo.infrastucture.convertor;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public final class PhoneToPromoClientConverter implements Converter<String, PromoCodeClient> {

    private final ClientIdentity clientIdentity;

    private final RealmIdentityProvider realmProvider;

    @SneakyThrows
    @Override
    public PromoCodeClient convert(String phone) {

        if (!phone.matches("^[0-9]{12}$"))
            return null;

        RealmIdentity identity = realmProvider.resolve();
        Client client = clientIdentity.getClient(new Realm(identity.getUuid()), phone);

        return new PromoCodeClient(client.getUuid());
    }
}

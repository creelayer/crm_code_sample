package com.creelayer.marketplace.crm.app;

import com.creelayer.marketplace.crm.common.reaml.Realm;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

@RequiredArgsConstructor
public final class RealmConvertor implements Converter<String, RealmIdentity> {

    @Override
    public Realm convert(String id) {


        if (id.contains(",")) {  //TODO: BUG. Front request invalid format
            String partId = id.substring(0, id.indexOf(","));
            if (partId.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
                return new Realm(UUID.fromString(partId));
        }


        if (id.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
            return new Realm(UUID.fromString(id));

        return null;
    }
}

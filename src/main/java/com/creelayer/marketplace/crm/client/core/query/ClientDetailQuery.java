package com.creelayer.marketplace.crm.client.core.query;

import java.util.UUID;

public record ClientDetailQuery(UUID realm, String phone, boolean withBalance) {

    public ClientDetailQuery(UUID realm, String phone) {
       this(realm, phone, false);
    }
}

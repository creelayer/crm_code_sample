package com.creelayer.marketplace.crm.common.reaml;

import lombok.AllArgsConstructor;

import java.util.UUID;

public @AllArgsConstructor
class Realm implements RealmIdentity {

    public UUID uuid;

    @Override
    public UUID getUuid() {
        return uuid;
    }
}

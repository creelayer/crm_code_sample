package com.creelayer.marketplace.crm.loyalty.core.model;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Realm implements RealmIdentity {

    @Setter
    public UUID uuid;
}

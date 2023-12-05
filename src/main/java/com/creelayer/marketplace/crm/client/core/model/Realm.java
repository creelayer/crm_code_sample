package com.creelayer.marketplace.crm.client.core.model;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Realm implements RealmIdentity {

    @Setter
    private UUID uuid;
}

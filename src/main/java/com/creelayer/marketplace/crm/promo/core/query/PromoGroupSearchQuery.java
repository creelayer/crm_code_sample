package com.creelayer.marketplace.crm.promo.core.query;


import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PromoGroupSearchQuery {

    public enum Status {
        ACTIVE, DISABLED
    }

    private UUID realm;

    private String search;

    private Status status;

    private int page;

    private int size = 50;

    @Default
    public PromoGroupSearchQuery(UUID realm) {
        this.realm = realm;
    }

    public PromoGroupSearchQuery(UUID realm, String search) {
        this.realm = realm;
        this.search = search;
    }
}

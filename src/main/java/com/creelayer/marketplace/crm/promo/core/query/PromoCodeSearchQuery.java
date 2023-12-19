package com.creelayer.marketplace.crm.promo.core.query;

import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PromoCodeSearchQuery {

    public enum State {
        ACTIVE, USED, ARCHIVED
    }

    public enum Status {
        ACTIVE, DISABLED
    }

    private UUID realm;

    private UUID client;

    private String search;

    private UUID group;

    private Status status;

    private State state;

    private int page;

    private int size = 50;

    @Default
    public PromoCodeSearchQuery(UUID realm) {
        this.realm = realm;
    }

    public PromoCodeSearchQuery(UUID realm, String search) {
        this.realm = realm;
        this.search = search;
    }
}

package com.creelayer.marketplace.crm.promo.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PromoCodeSearchQuery {

    public enum State {
        ACTIVE, USED, ARCHIVED
    }

    public enum Status {
        ACTIVE, DISABLED
    }

    private UUID client;

    private String search;

    private UUID group;

    private Status status;

    private State state;
}

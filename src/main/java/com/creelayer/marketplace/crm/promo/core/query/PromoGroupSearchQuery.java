package com.creelayer.marketplace.crm.promo.core.query;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromoGroupSearchQuery {

    public enum Status {
        ACTIVE, DISABLED
    }

    private String search;

    private Status status;
}

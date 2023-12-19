package com.creelayer.marketplace.crm.promo.core.query;

import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PromoCodeUsageSearchQuery {

    private UUID realm;

    private String search;

    private UUID code;

    private int page;

    private int size = 50;

    @Default
    public PromoCodeUsageSearchQuery(UUID realm) {
        this.realm = realm;
    }

    public PromoCodeUsageSearchQuery(UUID realm, String search) {
        this.realm = realm;
        this.search = search;
    }
}

package com.creelayer.marketplace.crm.promo.core.query;

import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PromoActionSearchQuery {

    private UUID realm;

    private UUID group;

    private String search;

    private int page;

    private int size = 50;

    @Default
    public PromoActionSearchQuery(UUID group, String search) {
        this.group = group;
        this.search = search;
    }
}

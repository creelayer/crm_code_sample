package com.creelayer.marketplace.crm.market.core.query;

import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MarketSearchQuery {

    private final UUID account;

    private String search;

    private int page;

    private int size = 50;

    @Default
    public MarketSearchQuery(UUID account) {
        this.account = account;
    }
}

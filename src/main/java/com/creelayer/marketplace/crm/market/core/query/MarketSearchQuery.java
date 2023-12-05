package com.creelayer.marketplace.crm.market.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MarketSearchQuery {

    private UUID account;

    private String search;
}

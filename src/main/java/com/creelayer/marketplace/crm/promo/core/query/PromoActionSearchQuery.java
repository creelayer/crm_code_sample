package com.creelayer.marketplace.crm.promo.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PromoActionSearchQuery {

    public UUID group;

    public String search;
}

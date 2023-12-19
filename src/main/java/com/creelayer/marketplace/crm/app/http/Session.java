package com.creelayer.marketplace.crm.app.http;

import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.market.core.projection.MarketShortDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Session {

    private Account account;

    @Getter
    private Market market;

    @Getter
    private List<Market> available;

    public Session(Account account) {
        this.account = account;
    }

    public Session setMarket(MarketShortDetail available) {
        this.market = new Market(available.getUuid(), available.getName(), available.getAccount().getUuid().equals(account.getUuid()));
        return this;
    }

    public Session managed(List<MarketShortDetail> markets) {
        this.available = markets
                .stream()
                .map(e -> new Market(e.getUuid(), e.getName(), e.getAccount().getUuid().equals(account.getUuid())))
                .collect(Collectors.toList());

        return this;
    }

    @AllArgsConstructor
    public static class Market {
        public UUID uuid;
        public String name;

        @Setter
        public Boolean owner;
    }

}

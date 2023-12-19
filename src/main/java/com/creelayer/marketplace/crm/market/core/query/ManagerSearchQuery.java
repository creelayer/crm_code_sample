package com.creelayer.marketplace.crm.market.core.query;

import com.creelayer.marketplace.crm.common.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ManagerSearchQuery {

    private final UUID market;

    private String search;

    private int page;

    private int size = 50;

    @Default
    public ManagerSearchQuery(UUID market) {
        this.market = market;
    }

    public boolean isEmail() {
        return search != null && search.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }
}

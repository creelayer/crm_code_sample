package com.creelayer.marketplace.crm.client.core.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ClientSearchQuery {

    private final UUID realm;

    private String search;

    private int page;

    private int size = 50;

    public boolean isPhone() {
        return search != null && search.matches("^[0-9]{12}$");
    }

    public boolean isEmail() {
        return search != null && search.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }
}

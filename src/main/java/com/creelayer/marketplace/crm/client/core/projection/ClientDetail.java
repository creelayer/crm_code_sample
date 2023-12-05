package com.creelayer.marketplace.crm.client.core.projection;

import com.creelayer.marketplace.crm.client.core.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientDetail {


    private final UUID uuid;

    private final String phone;

    private String email;

    private String name;

    private Balance balance;

    public ClientDetail(Client client) {
        this(client, null);
    }

    public ClientDetail(Client client, com.creelayer.marketplace.crm.client.core.model.Balance balance) {
        this.uuid = client.getUuid();
        this.phone = client.getPhone();
        this.email = client.getEmail();
        this.name = client.getName();
        this.balance = balance != null ? new Balance(balance) : null;
    }

    @Getter
    public static class Balance {

        private final UUID uuid;

        private final String currency;

        private final long amount;

        public Balance(com.creelayer.marketplace.crm.client.core.model.Balance balance) {
            this.uuid = balance.getUuid();
            this.currency = balance.getCurrency().name();
            this.amount = balance.getAmount();
        }
    }

}

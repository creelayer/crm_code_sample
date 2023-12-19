package com.creelayer.marketplace.crm.order.core.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearchQuery {

    public enum PayoutStatus {
        NONE, REQUIRED, COMPLETE
    }

    public UUID realm;

    public UUID customer;

    public String search;

    public LocalDate from;

    public LocalDate to;

    public List<String> status;

    public PayoutStatus payout;

    public int page;

    public int size = 50;

    public OrderSearchQuery(UUID realm, String search) {
        this.search = search;
    }

    public OrderSearchQuery(UUID realm, UUID customer) {
        this.realm = realm;
        this.customer = customer;
    }

    public boolean isPhone() {
        return search != null && search.matches("^[0-9]{12}$");
    }

    public boolean isCode() {
        return search != null && search.matches("^[0-9]{4}$");
    }

    public boolean isEmail() {
        return search != null && search.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }
}

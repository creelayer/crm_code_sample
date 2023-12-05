package com.creelayer.marketplace.crm.order.core.model;

import lombok.Getter;

@Getter
public class OrderContact {

    private final String name;

    private final String phone;

    private final String email;

    private final String comment;

    public OrderContact(String name, String phone, String email, String comment) {

        if (!phone.matches("^[0-9]{12}$"))
            throw new IllegalStateException("Invalid phone format");

        if (email != null && !email.matches("^(.+)@(\\S+)$"))
            throw new IllegalStateException("Invalid email format");

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
    }
}

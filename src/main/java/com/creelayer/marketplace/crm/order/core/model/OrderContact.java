package com.creelayer.marketplace.crm.order.core.model;

import com.creelayer.marketplace.crm.common.type.Email;
import com.creelayer.marketplace.crm.common.type.Phone;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public final class OrderContact {

    private final String name;

    private final Phone phone;

    private final Email email;

    private final String comment;

    @JsonCreator
    public OrderContact(String name, String phone, String email, String comment) {
        this.name = name;
        this.phone = new Phone(phone);
        this.email = new Email(email);
        this.comment = comment;
    }

    public OrderContact(String name, String phone, String email) {
        this.name = name;
        this.phone = new Phone(phone);
        this.email = new Email(email);
        this.comment = null;
    }

    public OrderContact(String name, Phone phone, Email email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = null;
    }
}

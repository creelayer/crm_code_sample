package com.creelayer.marketplace.crm.order.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "client")
@Immutable
public class OrderCustomer {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Basic
    private String name;

    @Basic
    private String phone;

    @Basic
    private String email;

    public OrderCustomer(UUID uuid, String name, String phone, String email) {

        if (!phone.matches("^[0-9]{12}$"))
            throw new IllegalStateException("Invalid phone format");

        if (email != null && !email.matches("^(.+)@(\\S+)$"))
            throw new IllegalStateException("Invalid email format");

        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

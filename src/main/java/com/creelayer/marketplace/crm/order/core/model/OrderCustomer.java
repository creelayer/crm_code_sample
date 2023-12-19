package com.creelayer.marketplace.crm.order.core.model;

import com.creelayer.marketplace.crm.common.type.Email;
import com.creelayer.marketplace.crm.common.type.Phone;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
@Immutable
public final class OrderCustomer {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Basic
    private String name;

    @Embedded
    private Phone phone;

    @Embedded
    private Email email;

    @JsonCreator
    public OrderCustomer(UUID uuid, String name, String phone, String email) {
        this.uuid = uuid;
        this.name = name;
        this.phone = new Phone(phone);
        this.email = new Email(email);
    }

    public OrderCustomer(UUID uuid, String name, Phone phone, Email email) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

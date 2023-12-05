package com.creelayer.marketplace.crm.account.core.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = {"uuid"}, callSuper = false)
@NoArgsConstructor
@Entity
public class Account extends Aggregate<Account> implements Principal {

    public enum Status {
        ACTIVE, DISABLED;
    }

    @Id
    private UUID uuid;

    @Basic
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(length = 50)
    private Status status = Status.ACTIVE;

    @Setter
    @Basic
    private String fullName;

    @Setter
    @Basic
    private String clientId;

    public Account(UUID uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    @Override
    public String getName() {
        return uuid.toString();
    }
}

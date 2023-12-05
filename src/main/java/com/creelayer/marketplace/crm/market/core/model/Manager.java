package com.creelayer.marketplace.crm.market.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Manager extends Aggregate<Manager> {

    public enum Status {
        INVITED, ACTIVE, DISABLED;
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne
    private Account account;

    @ManyToOne(optional = false)
    private Market market;

    @Setter
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(length = 50)
    private Status status = Status.ACTIVE;

    public Manager(Account account, Market market) {
        this.account = account;
        this.market = market;
    }

    @PrePersist
    public void prePersist() {
        registerEvent(new ManagerCreateEvent(this));
    }

    @PostRemove
    public void postRemove() {
        registerEvent(new ManagerRemoveEvent(this));
    }
}

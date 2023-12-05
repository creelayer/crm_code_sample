package com.creelayer.marketplace.crm.market.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ManagerAccount")
@Table(name = "account")
public class Account {

    @Id
    private UUID uuid;

    @Column(insertable=false, updatable=false)
    private String email;

    public Account(UUID uuid) {
        this.uuid = uuid;
    }
}

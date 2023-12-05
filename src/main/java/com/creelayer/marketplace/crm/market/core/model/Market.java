package com.creelayer.marketplace.crm.market.core.model;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.secutiry.Identity;
import com.creelayer.marketplace.crm.market.core.RandomString;
import com.creelayer.wallet.client.WalletAccess;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;


@Accessors(chain = true)
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE market SET deleted_at = now() WHERE uuid=?")
@Where(clause = "deleted_at IS NULL")
public class Market extends Aggregate<Market> implements Identity, UserDetails, RealmIdentity {

    public enum Status {
        CREATED, ACTIVE, DISABLED;
    }

    @Id
    @Getter
    @GeneratedValue
    private UUID uuid;

    @Getter

    @ManyToOne
    private Account account;

    @Getter
    @Setter
    @Basic
    private String url;

    @Getter
    @Basic
    private String name;

    @Getter
    @Setter
    @Basic
    private String phone;

    @Getter
    @Setter
    @Basic
    private String email;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(length = 50)
    private Status status = Status.CREATED;

    @Setter
    @Basic(optional = false)
    private String secret;

    @Getter
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Wallet wallet;

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST})
    private final Set<Manager> managers = new HashSet<>();

    @Setter
    private LocalDateTime deletedAt;

    public Market(Account account, String name) {
        this.account = account;
        this.name = name;
        this.secret = RandomString.generate();
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @PrePersist
    public void prePersist() {
        registerEvent(new MarketCreateEvent(this));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_MARKET_API"));
    }

    @Override
    public String getPassword() {
        return "{noop}" + secret;
    }

    @Override
    public String getUsername() {
        return uuid.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Wallet {

        public UUID balance;

        public WalletAccess access;
    }
}

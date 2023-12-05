package com.creelayer.marketplace.crm.promo.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE promo_code SET deleted_at = now() WHERE uuid=?")
@Where(clause = "deleted_at IS NULL")
@Table(indexes = {
        @Index(columnList = "code")
})
public class PromoCode extends Aggregate<PromoCode> {

    public enum Status {
        ACTIVE, DISABLED
    }

    public enum TYPE {
        PERCENT, AMOUNT
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne(optional = false)
    private PromoGroup group;

    @ManyToOne
    private PromoCodeClient owner;

    @Setter
    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private TYPE type;

    //TODO: Make discount object with own type ^
    @Basic(optional = false)
    private long discount;

    @Basic
    private Integer maxUses;

    @Setter
    @Basic(optional = false)
    private LocalDateTime expiredAt;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PromoCondition> conditions = new ArrayList<>();

    @OneToMany(mappedBy = "code")
    private final List<PromoCodeUses> uses = new ArrayList<>();

    private LocalDateTime deletedAt;

    public PromoCode(PromoGroup group, PromoCodeClient owner, Status status, String name, String code, TYPE type, long discount, LocalDateTime expiredAt, Integer maxUses) {
        this.group = group;
        this.owner = owner;
        this.status = status;
        this.name = name;
        this.code = code;
        this.type = type;
        this.discount = discount;
        this.expiredAt = expiredAt;
        this.maxUses = maxUses;
    }

    public PromoCode(PromoGroup group, Status status, String name, String code, TYPE type, long discount, LocalDateTime expiredAt, Integer maxUses) {
        this.group = group;
        this.status = status;
        this.name = name;
        this.code = code;
        this.type = type;
        this.discount = discount;
        this.expiredAt = expiredAt;
        this.maxUses = maxUses;
    }

    public boolean isActive() {
        return status.equals(Status.ACTIVE) && isNonExpired();
    }

    public boolean isNonExpired() {
        return expiredAt.isAfter(LocalDateTime.now());
    }

    public PromoCode setConditions(List<PromoCondition> conditions) {
        this.conditions.clear();
        this.conditions.addAll(conditions);
        return this;
    }
}

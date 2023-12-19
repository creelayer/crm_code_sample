package com.creelayer.marketplace.crm.promo.core.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE promo_group SET deleted_at = now() WHERE uuid=?")
public class PromoGroup extends Aggregate<PromoGroup> {

    public enum Status {
        ACTIVE, DISABLED
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @Setter
    @Basic(optional = false)
    private String name;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Getter
    private LocalDateTime deletedAt;

    public PromoGroup(UUID uuid, String name) {
        this.realm = new Realm(uuid);
        this.name = name;
    }
}

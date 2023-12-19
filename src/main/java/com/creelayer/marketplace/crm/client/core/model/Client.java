package com.creelayer.marketplace.crm.client.core.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "idx_p_m_u", columnList = "phone, realm", unique = true)}
)
public class Client extends Aggregate<Client> {

    @Getter
    @Id
    @GeneratedValue
    private UUID uuid;

    @Getter
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @Getter
    @Basic(optional = false)
    private String phone;

    @Getter
    @Setter
    @Basic
    private String name;

    @Getter
    @Setter
    @Basic
    private String email;

    @Getter
    @Setter
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private ClientBalanceKey balanceAccessKey;

    public Client(Realm realm, String phone) {
        this.realm = realm;
        this.phone = phone;
    }

    public Client initRegistration() {
        registerEvent(new ClientRegisterEvent(this));
        return this;
    }
}

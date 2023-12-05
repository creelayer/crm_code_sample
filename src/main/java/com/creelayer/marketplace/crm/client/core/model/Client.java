package com.creelayer.marketplace.crm.client.core.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_p_m_u", columnList = "phone, realm", unique = true)}
)
public class Client extends Aggregate<Client> {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @Basic(optional = false)
    private String phone;

    @Setter
    @Basic
    private String name;

    @Setter
    @Basic
    private String email;

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

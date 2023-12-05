package com.creelayer.marketplace.crm.promo.core.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PromoCodeUses extends Aggregate<PromoCodeUses> {

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne(optional = false)
    private PromoCode code;

    @ManyToOne
    private PromoCodeClient client;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> data = new HashMap<>();

    public PromoCodeUses(PromoCodeClient client, PromoCode code) {
        this.client = client;
        this.code = code;
    }

    public void addProperty(String name, String value) {
        data.put(name, value);
    }

}

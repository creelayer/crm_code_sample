package com.creelayer.marketplace.crm.loyalty.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@NoArgsConstructor
@Entity
public class LoyaltySetting extends Aggregate<LoyaltySetting> {

    public enum TYPE {
        CASHBACK, REGISTRATION_PROMO_CODE
    }

    @Getter
    @Id
    @GeneratedValue
    private UUID uuid;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @Getter
    @Enumerated(EnumType.STRING)
    private TYPE type;

    @Getter
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode data;

    public LoyaltySetting(Realm realm, TYPE type) {
        this.realm = realm;
        this.type = type;
    }

    public void setData(Object setting) {
        var objectMapper = new ObjectMapper();
        data = objectMapper.valueToTree(setting);
    }

    public <T> T getData(Class<T> tClass) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.treeToValue(data, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

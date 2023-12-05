package com.creelayer.marketplace.crm.promo.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE promo_code SET deleted_at = now() WHERE uuid=?")
@Where(clause = "deleted_at IS NULL")
public class PromoAction extends Aggregate<PromoAction> {

    public enum Type {
        SIMPLE, DELIVERY, RELATED
    }

    public enum Subtype {
        ACTION, DELIVERY, GIFT
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne(optional = false)
    private PromoGroup group;

    @Setter
    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Subtype subtype;

    @Setter
    @Basic(optional = false)
    private LocalDateTime expiredAt;

    @Setter
    @Basic(optional = false)
    private boolean timer;

    @org.hibernate.annotations.Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode features;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCondition> conditions = new ArrayList<>();

    @Setter
    @org.hibernate.annotations.Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> description;

    @Setter
    @Basic(optional = false)
    @org.hibernate.annotations.Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> locales;

    private LocalDateTime deletedAt;

    public PromoAction(PromoGroup group, String name, Type type, Subtype subtype, LocalDateTime expiredAt, JsonNode features) {
        this.group = group;
        this.name = name;
        this.type = type;
        this.subtype = subtype;
        this.expiredAt = expiredAt;
        this.features = features;
        this.locales = Map.of("default", name);
    }

    public PromoAction setFeatures(Object setting) {
        var objectMapper = new ObjectMapper();
        features = objectMapper.valueToTree(setting);
        return this;
    }

    public <T> T getFeatures(Class<T> tClass) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.treeToValue(features, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PromoAction setConditions(List<PromoCondition> conditions) {
        this.conditions.clear();
        this.conditions.addAll(conditions);
        return this;
    }

    public boolean isNonExpired() {
        return expiredAt.isAfter(LocalDateTime.now());
    }
}

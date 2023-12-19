package com.creelayer.marketplace.crm.promo.core.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PromoCondition {

    @Getter
    @Id
    @GeneratedValue
    private UUID uuid;

    @Getter
    @Column(columnDefinition = "TEXT")
    private String path;

    @Getter
    @Column(columnDefinition = "TEXT")
    private String labels;

    @Getter
    @Setter
    @Basic
    private Long priceFrom;

    @Getter
    @Setter
    @Basic
    private Long priceTo;

    @Getter
    @Setter
    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private List<String> skus = new ArrayList<>();

    @Getter
    @Setter
    @Basic
    private boolean invert;

    @CreationTimestamp
    @Basic(optional = false)
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Basic(optional = false)
    private LocalDateTime updatedAt;

    public PromoCondition(String path, String labels) {
        this.path = path;
        this.labels = labels;
    }
}

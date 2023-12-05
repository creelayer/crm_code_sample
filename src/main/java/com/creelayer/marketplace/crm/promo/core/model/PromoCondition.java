package com.creelayer.marketplace.crm.promo.core.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class PromoCondition {

    @Id
    @GeneratedValue
    private UUID uuid;

    @Column(columnDefinition = "TEXT")
    private String path;

    @Column(columnDefinition = "TEXT")
    private String labels;

    @Setter
    @Basic
    private Long priceFrom;

    @Setter
    @Basic
    private Long priceTo;

    @Setter
    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private List<String> skus = new ArrayList<>();

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

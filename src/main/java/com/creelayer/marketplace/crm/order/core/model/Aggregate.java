package com.creelayer.marketplace.crm.order.core.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Aggregate<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {

    @Setter
    @Getter
    @CreationTimestamp
    @Basic(optional = false)
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Getter
    @Setter
    @UpdateTimestamp
    @Basic(optional = false)
    protected LocalDateTime updatedAt;

}

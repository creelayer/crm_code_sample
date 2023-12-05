package com.creelayer.marketplace.crm.promo.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "client")
@Immutable
public class PromoCodeClient {

    @Id
    private UUID uuid;
}

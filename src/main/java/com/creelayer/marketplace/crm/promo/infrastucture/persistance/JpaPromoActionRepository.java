package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.outgoing.PromoActionRepository;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoActionRepository extends JpaRepository<PromoAction, UUID>, PromoActionRepository {
}

package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPromoGroupRepository extends JpaRepository<PromoGroup, UUID>, PromoGroupRepository {

}

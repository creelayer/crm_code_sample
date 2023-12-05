package com.creelayer.marketplace.crm.loyalty.infrastructure.persistance;

import com.creelayer.marketplace.crm.loyalty.core.incoming.LoyaltySettingSearch;
import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import com.creelayer.marketplace.crm.loyalty.core.model.Realm;
import com.creelayer.marketplace.crm.loyalty.core.outgoing.LoyaltySettingRepository;
import com.creelayer.marketplace.crm.loyalty.core.projection.LoyaltySettingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaLoyaltySettingRepository extends JpaRepository<LoyaltySetting, UUID>, LoyaltySettingRepository, LoyaltySettingSearch {

    @Query("SELECT s FROM LoyaltySetting s WHERE s.realm = :realm AND s.type = :type")
    Optional<LoyaltySettingView> getSetting(Realm realm, LoyaltySetting.TYPE type);

}

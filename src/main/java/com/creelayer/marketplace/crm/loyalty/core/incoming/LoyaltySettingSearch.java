package com.creelayer.marketplace.crm.loyalty.core.incoming;

import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import com.creelayer.marketplace.crm.loyalty.core.model.Realm;
import com.creelayer.marketplace.crm.loyalty.core.projection.LoyaltySettingView;

import java.util.Optional;

public interface LoyaltySettingSearch {
    Optional<LoyaltySettingView> getSetting(Realm realm, LoyaltySetting.TYPE type);
}

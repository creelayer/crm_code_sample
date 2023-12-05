package com.creelayer.marketplace.crm.loyalty.core.outgoing;

import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import com.creelayer.marketplace.crm.loyalty.core.model.Realm;

import java.util.List;
import java.util.Optional;


public interface LoyaltySettingRepository {

    List<LoyaltySetting> findAllByRealm(Realm realm);

    Optional<LoyaltySetting> findAByRealmAndType(Realm realm, LoyaltySetting.TYPE type);


    LoyaltySetting save(LoyaltySetting setting);

}

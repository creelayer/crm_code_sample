package com.creelayer.marketplace.crm.loyalty.core;

import com.creelayer.marketplace.crm.loyalty.core.model.Realm;
import com.creelayer.marketplace.crm.loyalty.core.model.RegistrationPromoSetting;
import com.creelayer.marketplace.crm.loyalty.core.outgoing.LoyaltySettingRepository;
import com.creelayer.marketplace.crm.loyalty.core.command.RegistrationPromoSettingCommand;
import com.creelayer.marketplace.crm.loyalty.core.incoming.RegistrationPromoManager;
import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class LoyaltySettingService implements RegistrationPromoManager {

    private LoyaltySettingRepository settingRepository;


    @Override
    public void setting(RegistrationPromoSettingCommand command) {

        LoyaltySetting setting = settingRepository
                .findAByRealmAndType(new Realm(command.getRealm()), LoyaltySetting.TYPE.REGISTRATION_PROMO_CODE)
                .orElseGet(() ->
                        new LoyaltySetting(new Realm(command.getRealm()), LoyaltySetting.TYPE.REGISTRATION_PROMO_CODE));

        setting.setData(new RegistrationPromoSetting(command.getDiscount(), command.getValidity()));
        settingRepository.save(setting);
    }
}

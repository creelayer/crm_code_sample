package com.creelayer.marketplace.crm.client.infrastucture.listener;

import com.creelayer.marketplace.crm.client.core.model.ClientRegisterEvent;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.loyalty.core.incoming.LoyaltySettingSearch;
import com.creelayer.marketplace.crm.loyalty.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.command.CreatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeManage;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupManage;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.settings.core.SettingManage;
import com.creelayer.marketplace.crm.settings.core.SettingSearch;
import com.creelayer.marketplace.crm.settings.core.UpsertSettingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public final class ClientRegistrationPromoListener {

    private static final String REGISTRATION_PROMO_GROUP_SETTING = "client:registration:promo:group";

    private static final String REGISTRATION_PROMO_GROUP_NAME = "User registration promo code";
    private static final String REGISTRATION_PROMO_MESSAGE = "Registration promo code";

    private final LoyaltySettingSearch loyaltySettingSearch;

    private final PromoCodeManage promoCodeManage;

    private final SettingSearch settingSearch;

    private final SettingManage settingManage;

    private final PromoGroupManage groupManage;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void generateRegistrationPromoCode(ClientRegisterEvent event) {
        try {
            Client client = event.aggregate();
            Realm realm = new Realm(client.getRealm().getUuid());

            loyaltySettingSearch.
                    getSetting(realm, LoyaltySetting.TYPE.REGISTRATION_PROMO_CODE)
                    .map(e -> e.getData(RegistrationPromoSetting.class))
                    .filter(e -> e.discount != null && e.discount > 0)
                    .ifPresent(setting -> generatePromoCode(realm, client, setting));
        } catch (Exception e) {
            log.error("Can't create promo code no client registration");
        }
    }

    private void generatePromoCode(RealmIdentity realm, Client client, RegistrationPromoSetting setting) {

        GeneratePromoCodeCommand command = GeneratePromoCodeCommand.builder()
                .group(getGroupUUID(realm))
                .owner(client.getUuid())
                .name(REGISTRATION_PROMO_MESSAGE)
                .type(GeneratePromoCodeCommand.TYPE.PERCENT)
                .discount(setting.discount)
                .expiredAt(LocalDateTime.now().plusMonths(setting.validity))
                .build();

        promoCodeManage.generate(command);
    }

    private UUID getGroupUUID(RealmIdentity realm) {
        return settingSearch.find(realm.getUuid(), REGISTRATION_PROMO_GROUP_SETTING)
                .map(e -> UUID.fromString(e.getValue(String.class)))
                .orElseGet(() -> {
                    CreatePromoGroupCommand command = new CreatePromoGroupCommand(REGISTRATION_PROMO_GROUP_NAME);
                    PromoGroup group = groupManage.create(new com.creelayer.marketplace.crm.promo.core.model.Realm(realm.getUuid()), command);
                    settingManage.upsert(new UpsertSettingCommand(realm.getUuid(), REGISTRATION_PROMO_GROUP_SETTING, group.getUuid()));
                    return group.getUuid();
                });
    }


    private record RegistrationPromoSetting(Integer discount, Integer validity) {
    }

}

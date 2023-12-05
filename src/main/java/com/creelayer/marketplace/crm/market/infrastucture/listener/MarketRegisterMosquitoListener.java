package com.creelayer.marketplace.crm.market.infrastucture.listener;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.market.core.model.MarketCreateEvent;
import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.mosquito.client.MosquitoChannelServiceApi;
import com.creelayer.mosquito.client.MosquitoIntegrationApi;
import com.creelayer.mosquito.client.MosquitoMessageServiceApi;
import com.creelayer.mosquito.client.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "mosquito.active")
public final class MarketRegisterMosquitoListener {

    private final AccountRepository accountRepository;
    private final MosquitoIntegrationApi mosquitoIntegrationApi;
    private final MosquitoChannelServiceApi mosquitoChannelServiceApi;
    private final MosquitoMessageServiceApi mosquitoMessageServiceApi;
    private final SettingApi<Setting> sip;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(MarketCreateEvent event) {
        Market market = event.aggregate();
        createMosquitoSettings(market);
    }

    private void createMosquitoSettings(Market market) {

        Account account = accountRepository.findById(market.getAccount().getUuid()).orElseThrow();
        Setting setting = sip.one(market.getUuid().toString(), "market:integration:wallet").orElseThrow();

        Map<String, String> config = (Map<String, String>) setting.getValue();

        config.computeIfAbsent("mosquito:project:uuid", key -> {

            ProjectIntegrate integrate = new ProjectIntegrate(
                    new ProjectIntegrate.Account(
                            account.getUuid(),
                            account.getEmail(),
                            account.getFullName()
                    ),
                    new ProjectIntegrate.Project(
                            market.getUuid(),
                            "Market " + market.getName()
                    )
            );

            ProjectResponse response = mosquitoIntegrationApi.create(integrate).content;
            return response.uuid.toString();
        });

        config.computeIfAbsent("channel:order:uuid", key -> {
            Channel channel = new Channel();
            channel.name = "Market order channel";
            ChannelResponse response = mosquitoChannelServiceApi
                    .create(UUID.fromString(config.get("mosquito:project:uuid")), channel).content;
            return response.uuid.toString();
        });

        config.computeIfAbsent("message:order:uuid", key -> {
            Message message = new Message();
            message.name = "Market new order message";
            message.type = Message.Type.TELEGRAM;
            message.text = "У вас нові замовленя: {{count}}";
            MessageResponse response = mosquitoMessageServiceApi
                    .create(UUID.fromString(config.get("mosquito:project:uuid")), message).content;
            return response.uuid.toString();
        });

        config.computeIfAbsent("message:order:wait:uuid", key -> {
            Message message = new Message();
            message.name = "Market wait order message";
            message.type = Message.Type.TELEGRAM;
            message.text = "У Вас є неопрацьовані замовленя: {{count}}";
            MessageResponse response = mosquitoMessageServiceApi
                    .create(UUID.fromString(config.get("mosquito:project:uuid")), message).content;
            return response.uuid.toString();
        });

        config.computeIfAbsent("message:opt:uuid", key -> {
            Message message = new Message();
            message.name = "Message opt request";
            message.type = Message.Type.SMS;
            message.text = "Ваш код входу: {{opt}}";
            MessageResponse response = mosquitoMessageServiceApi
                    .create(UUID.fromString(config.get("mosquito:project:uuid")), message).content;
            return response.uuid.toString();
        });

        sip.put(setting);
    }
}

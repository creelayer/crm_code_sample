package com.creelayer.marketplace.crm.market.infrastucture.listener;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.market.core.model.ManagerCreateEvent;
import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.mosquito.client.MosquitoMessageServiceApi;
import com.creelayer.mosquito.client.MosquitoMessengerApi;
import com.creelayer.mosquito.client.dto.DirectMessage;
import com.creelayer.mosquito.client.dto.Message;
import com.creelayer.mosquito.client.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public final class ManagerRegistrationInvocationListener {

    @Value("${crm.base-url}")
    public String marketplaceUrl;

    private final AccountRepository accountRepository;

    private final MosquitoMessengerApi api;

    private final MosquitoMessageServiceApi mosquitoMessageServiceApi;

    private final SettingApi<Setting> sip;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(ManagerCreateEvent event) {
        Manager manager = event.aggregate();
        sendInviteMessageMessage(manager);
    }

    private void sendInviteMessageMessage(Manager manager) {

        Setting setting = sip.one(manager.getMarket().getUuid().toString(), "market:integration:wallet").orElseThrow();

        Map<String, String> config = setting.getValue(Map.class);

        if (!config.containsKey("mosquito:project::uuid"))
            return;

        config.computeIfAbsent("message:manager:invite:uuid", key -> {
            Message message = new Message();
            message.name = "Market manager invite message";
            message.type = Message.Type.TELEGRAM;
            message.text = "Вас запросили до керування магазином: {{url}}";
            MessageResponse response = mosquitoMessageServiceApi
                    .create(UUID.fromString(config.get("mosquito:project::uuid")), message).content;
            return response.uuid.toString();
        });

        Account account = accountRepository.findById(manager.getAccount().getUuid()).orElseThrow();

        api.send(new DirectMessage(config.get("message:manager:invite:uuid"), account.getEmail(), Map.of("url", marketplaceUrl)));
    }
}

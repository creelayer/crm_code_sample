package com.creelayer.marketplace.crm.market.infrastucture.listener;

import com.creelayer.marketplace.crm.market.core.model.MarketCreateEvent;
import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public final class MarketCreateSettingsListener {

    private final ObjectMapper mapper;

    private final SettingApi<Setting> sip;

    @Order(-1)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(MarketCreateEvent event) {
        Market market = event.aggregate();
        createDefaultSettings(market);
    }

    private void createDefaultSettings(Market market) {
        sip.one(market.getUuid().toString(), "market:integration:wallet")
                .orElseGet(() -> {
                    Setting entity = new Setting(market.getUuid(), "market:integration:wallet");
                    entity.description = "Market integration wallet";
                    entity.editable = false;
                    entity.deletable = false;
                    entity.access = Setting.Access.PRIVATE;
                    entity.setValue(new HashMap<String, String>());
                    sip.put(entity);
                    return entity;
                });

        List<Setting> settings = sip.range(() -> new String[]{market.getUuid().toString()}, () -> new Object[]{market.getUuid().toString(), SettingApi.END});
        settings.stream()
                .filter(item -> item.name.equals("site:name"))
                .findFirst()
                .orElseGet(() -> {
                    JsonNode node = getJNode("classpath:defaults/market/site.name.config.json");
                    Setting setting = new Setting(market.getUuid(), "site:name", node);
                    setting.description = "Site name";
                    setting.deletable = false;
                    sip.put(setting);
                    return setting;
                });


        settings.stream()
                .filter(item -> item.name.equals("menu:mobile:phone"))
                .findFirst()
                .orElseGet(() -> {
                    JsonNode node = getJNode("classpath:defaults/market/mobile.menu.phones.config.json");
                    Setting setting = new Setting(market.getUuid(), "menu:mobile:phone", node);
                    setting.description = "Mobile menu phone";
                    setting.deletable = false;
                    sip.put(setting);
                    return setting;
                });


        settings.stream()
                .filter(item -> item.name.equals("main:phone:list"))
                .findFirst()
                .orElseGet(() -> {
                    JsonNode node = getJNode("classpath:defaults/market/main.phones.config.json");
                    Setting setting = new Setting(market.getUuid(), "main:phone:list", node);
                    setting.description = "Main phone list";
                    setting.deletable = false;
                    sip.put(setting);
                    return setting;
                });

        settings.stream()
                .filter(item -> item.name.equals("contact:messengers"))
                .findFirst()
                .orElseGet(() -> {
                    JsonNode node = getJNode("classpath:defaults/market/messengers.config.json");
                    Setting setting = new Setting(market.getUuid(), "contact:messengers", node);
                    setting.description = "Contact messengers";
                    setting.deletable = false;
                    sip.put(setting);
                    return setting;
                });

        settings.stream()
                .filter(item -> item.name.equals("default:page:meta"))
                .findFirst()
                .orElseGet(() -> {
                    JsonNode node = getJNode("classpath:defaults/market/meta.config.json");
                    Setting setting = new Setting(market.getUuid(), "default:page:meta", node);
                    setting.description = "Default meta";
                    setting.deletable = false;
                    sip.put(setting);
                    return setting;
                });
    }

    private JsonNode getJNode(String name) {
        try {
            return mapper.readTree(ResourceUtils.getFile(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

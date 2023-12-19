package com.creelayer.marketplace.crm.settings.infrastucture;

import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.marketplace.crm.settings.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SettingsApiProvider implements
        SettingManage,
        SettingFinder,
        QueryHandler<SearchSettingQuery, List<SettingView>>
{

    private final SettingApi<Setting> sip;

    @Override
    public List<SettingView> ask(SearchSettingQuery query) {
        return sip.range(
                        () -> new String[]{query.realm().toString()},
                        () -> new Object[]{query.realm().toString(), SettingApi.END}
                )
                .stream()
                .map(e -> new SettingView(e.name, e.type.toString(), e.getValue()))
                .toList();
    }

    @Override
    public Optional<SettingView> find(UUID realm, String name) {
        return sip.one(() -> new String[]{realm.toString(), name})
                .map(e -> new SettingView(e.name, e.type.toString(), e.getValue()));
    }

    @Override
    public void upsert(UpsertSettingCommand command) {
        Setting setting = new Setting(command.getRealm(), command.getName(), command.getValue());
        setting.description = command.getDescription();
        sip.put(setting);
    }



    @Override
    public void delete(UUID realm, String name) {
        Setting setting = sip.one(() -> new String[]{realm.toString(), name})
                .orElseThrow(() -> new SettingException("Setting not found"));
        sip.delete(setting);
    }
}

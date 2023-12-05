package com.creelayer.marketplace.crm.settings.core;

import java.util.UUID;

public interface SettingManage {

    void upsert(UpsertSettingCommand command);

    void delete(UUID realm, String name);
}

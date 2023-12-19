package com.creelayer.marketplace.crm.settings.core;

import java.util.Optional;
import java.util.UUID;

public interface SettingFinder {

    Optional<SettingView> find(UUID realm, String name);

}

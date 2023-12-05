package com.creelayer.marketplace.crm.settings.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SettingSearch {

    Optional<SettingView> find(UUID realm, String name);

    List<SettingView> search(SearchSettingQuery query);

}

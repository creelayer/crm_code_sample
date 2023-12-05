package com.creelayer.marketplace.crm.settings.core;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UpsertSettingCommand {

    private final UUID realm;

    private final String name;

    private final Object value;

    private String description;
}

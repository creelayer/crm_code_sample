package com.creelayer.marketplace.crm.settings.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SettingView {

    private final String name;

    private final String type;

    private final Object value;

    private String description;

    public <T> T getValue(Class<T> tClass) {
        return tClass.cast(value);
    }
}

package com.creelayer.marketplace.crm.settings.http.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateSettingRequest {

    @NotEmpty(message = "SettingView name can't be null")
    @Length(max = 255)
    @Pattern(regexp = "^[a-z0-9.:_\\-]+$", message = "Invalid name format")
    public String name;

    @NotNull(message = "SettingView value can't be null")
    public Object value;

    @Length(max = 255)
    public String description;
}

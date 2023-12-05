package com.creelayer.marketplace.crm.client.http.dto;

import lombok.Data;

import jakarta.validation.constraints.Pattern;

@Data
public class ClientSearchFilter {

    @Pattern(regexp = "^[0-9a-zа-яёіїєґ \\-]+$", message = "Invalid search", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public String search;
}

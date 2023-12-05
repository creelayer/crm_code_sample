package com.creelayer.marketplace.crm.market.http.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MarketSearchFilter {

    @Pattern(regexp = "^[0-9a-zа-яёіїєґ \\-]+$", message = "Invalid search", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public String search;
}

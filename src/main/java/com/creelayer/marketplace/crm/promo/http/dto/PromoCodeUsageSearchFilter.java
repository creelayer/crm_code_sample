package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PromoCodeUsageSearchFilter {

    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "Invalid search", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public String search;

    public UUID code;
}

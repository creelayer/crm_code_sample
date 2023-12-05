package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoGroupSearchFilter {

    public enum Status {
        ACTIVE, DISABLED
    }

    @Pattern(regexp = "^[0-9a-zа-яёіїєґ \\-]+$", message = "Invalid search", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public String search;

    public Status status;
}

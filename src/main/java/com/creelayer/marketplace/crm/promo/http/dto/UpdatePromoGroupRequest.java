package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdatePromoGroupRequest {

    public enum Status {
        ACTIVE, DISABLED
    }

    @NotNull
    public Status status;

    @NotEmpty
    public String name;
}

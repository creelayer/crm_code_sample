package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreatePromoGroupRequest {

    @NotEmpty
    public String name;

}

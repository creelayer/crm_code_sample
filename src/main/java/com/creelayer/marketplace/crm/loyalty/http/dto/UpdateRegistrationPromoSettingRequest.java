package com.creelayer.marketplace.crm.loyalty.http.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class UpdateRegistrationPromoSettingRequest {

    @Min(1)
    @Max(100)
    public Integer discount;

    @Min(1)
    @Max(9999)
    public Integer validity;
}

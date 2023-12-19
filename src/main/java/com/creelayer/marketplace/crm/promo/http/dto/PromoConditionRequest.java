package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class PromoConditionRequest {


    @NotEmpty
    public String path;

    @NotEmpty
    public String labels;

    @Min(1)
    public Long priceFrom;

    @Min(1)
    public Long priceTo;

    public List<String> skus;

    public boolean invert;

}

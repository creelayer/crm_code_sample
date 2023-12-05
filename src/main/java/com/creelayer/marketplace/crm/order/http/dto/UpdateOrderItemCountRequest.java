package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderItemCountRequest {

    @NotNull
    public String sku;

    @Min(1)
    public int count;
}

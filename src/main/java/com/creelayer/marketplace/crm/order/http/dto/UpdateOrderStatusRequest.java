package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateOrderStatusRequest {

    @NotBlank
    @Length(max = 255)
    @Pattern(regexp = "^[0-9a-zA-Z.:_\\-]{1,50}$", message = "Invalid status")
    public String status;

    @Length(max = 5000)
    public String comment;
}

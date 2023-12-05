package com.creelayer.marketplace.crm.market.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CreateMarketRequest {

    @NotEmpty
    @Length(max = 255)
    public String name;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{12}$")
    public String phone;

    @Length(max = 255)
    @Email
    public String email;
}

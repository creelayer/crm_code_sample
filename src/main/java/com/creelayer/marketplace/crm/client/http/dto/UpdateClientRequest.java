package com.creelayer.marketplace.crm.client.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {

    @Length(max = 255)
    @Email
    public String email;

    @Length(max = 255)
    public String name;
}

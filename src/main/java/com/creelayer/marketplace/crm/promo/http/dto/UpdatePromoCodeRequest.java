package com.creelayer.marketplace.crm.promo.http.dto;

import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromoCodeRequest {

    @NotNull
    public PromoGroup.Status status = PromoGroup.Status.ACTIVE;

    @NotEmpty
    public String name;

    @NotNull
    public LocalDateTime expiredAt;
}

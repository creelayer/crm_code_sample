package com.creelayer.marketplace.crm.promo.http.dto;

import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GeneratePromoCodeRequest {

    @Min(1)
    public int count = 1;

    @NotNull
    public PromoGroup.Status status = PromoGroup.Status.ACTIVE;

    @NotEmpty
    public String name;

    @Length(max = 10)
    public String code;

    @Min(1)
    public Integer maxUses = 1;

    @Min(1)
    @Max(999999)
    public long discount;

    @NotNull
    public PromoCode.TYPE type;

    @NotNull
    public LocalDateTime expiredAt;

    public List<@Valid PromoConditionRequest> conditions;

}

package com.creelayer.marketplace.crm.promo.http.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
public class UpdatePromoActionRequest {

    @NotEmpty
    public String name;

    @NotNull
    public Map<@Length(max = 7) String, @Length(max = 255) String> locales;

    public boolean timer;

    @NotNull
    public JsonNode features;

    @NotNull
    public LocalDateTime expiredAt;

    public Map<String, @Length(max = 50_000) String> description;

}

package com.creelayer.marketplace.crm.promo.core.projection;

import com.creelayer.marketplace.crm.promo.core.model.PromoCode;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromoCodeSearchUserResult {

    UUID getUuid();

    String getCode();

    String getName();

    long getDiscount();

    PromoCode.TYPE getType();

    LocalDateTime getExpiredAt();
}

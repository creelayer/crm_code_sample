package com.creelayer.marketplace.crm.promo.core.projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PromoCodeViewDetail  {
    UUID getUuid();

    Group getGroup();

    String getName();

    String getCode();

    String getType();

    Long getDiscount();

    String getStatus();

    Integer getMaxUses();

    LocalDateTime getExpiredAt();

    List<Condition> getConditions();

    interface Group{
        UUID getUuid();

        String getName();
    }
}

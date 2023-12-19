package com.creelayer.marketplace.crm.promo.core.projection;

import java.util.UUID;

public interface PromoDetailView {
    UUID getUuid();
    String getName();
    String getStatus();
}

package com.creelayer.marketplace.crm.promo.core.projection;

import java.util.List;
import java.util.UUID;

public interface Condition {

    UUID getUuid();

    String getPath();

    String getLabels();

    Long getPriceFrom();

    Long getPriceTo();

    List<String> getSkus();

    boolean isInvert();
}
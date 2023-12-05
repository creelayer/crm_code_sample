package com.creelayer.marketplace.crm.order.http.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderApiSearchResultResponse {
    UUID getUuid();

    long getCode();

    String getStatus();

    List<Item> getItems();

    LocalDateTime getCreatedAt();

    interface Item {

        String getSku();

        String getName();

        String getImage();

    }
}

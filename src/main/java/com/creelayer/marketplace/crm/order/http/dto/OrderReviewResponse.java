package com.creelayer.marketplace.crm.order.http.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class OrderReviewResponse {

    public UUID uuid;

    public long code;

    public String payment;

    public LocalDateTime createdAt;

    public List<Item> items;

    public static class Item {

        public String sku;

        public String name;

        public long amount;

        public int count;

    }
}

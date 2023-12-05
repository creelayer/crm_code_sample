package com.creelayer.marketplace.crm.order.http.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class OrderApiDetailResponse {

    public UUID uuid;

    public long code;

    public Contact contact;

    public Status status;

    public OrderPayment payment;

    public List<Item> items;

    public LocalDateTime createdAt;

    public Summary summary;

    public static class Summary {
        public long summary;
    }

    public static class Status {
        public String name;
    }

    public static class Contact {

        public String name;

        public String phone;

        public String email;
    }

    public static class OrderPayment {
        public String type;
    }

    public static class Item {

        public String sku;

        public String name;

        public String url;

        public String image;

        public long amount;

        public int count;

        public long total;
    }
}

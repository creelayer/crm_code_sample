package com.creelayer.marketplace.crm.order.http.dto;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OrderShortDetailResponse {

    public UUID uuid;

    public List<Item> items;

    @AllArgsConstructor
    public static class Item {

        public String name;

        public String sku;

        public int count;

        public int total;
    }
}

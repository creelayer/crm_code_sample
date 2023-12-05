package com.creelayer.marketplace.crm.order.http.dto;

import com.creelayer.activity.data.ActivityLogIdentity;
import com.creelayer.marketplace.crm.order.core.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ActivityLogIdentity(value = "uuid", type = Order.class)
public class OrderViewResponse {

    public UUID uuid;

    public String code;

    public String status;

    public String payoutStatus;

    public Delivery delivery;

    public Payment payment;

    public List<Payout> payouts;

    public Customer customer;

    public Customer contact;

    public List<Item> items;

    public PromoCode promoCode;

    public LocalDateTime createdAt;

    public Summary summary;

    public static class PromoCode {

        public String type;

        public String code;

        public long discount;
    }

    public static class Payout {

        public long amount;

        public String description;
    }

    public static class Payment {

        public String type;

        public boolean useBalance;
    }

    public static class Invoice {
        public UUID uuid;
    }

    public static class Delivery {

        public String type;
        public String address;
    }

    public static class Customer {

        public UUID uuid;

        public String name;

        public String phone;

        public String email;
    }


    public static class Contact {

        public String name;

        public String phone;

        public String email;
    }

    public static class Item {

        public String name;

        public String sku;

        public int count;

        public long amount;

        public long total;

        public long discount;

        public long summary;

        public PromoCode promoCode;
    }

    public static class Summary {

        public long total;

        public long itemsDiscount;

        public long itemsSummary;

        public long discount;

        public long summary;

    }
}

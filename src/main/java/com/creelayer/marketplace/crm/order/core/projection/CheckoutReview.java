package com.creelayer.marketplace.crm.order.core.projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CheckoutReview {

    UUID getUuid();

    long getCode();

    Payment getPayment();

    List<Item> getItems();

    LocalDateTime getCreatedAt();

    interface Payment {
        String getType();
    }

    interface Item {

         String getSku();
        
         String getName();

         int getCount();

         long getAmount();

         long getTotal();

         long getDiscount();

         long getSummary();
    }
}

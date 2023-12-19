package com.creelayer.marketplace.crm.order.core.projection;

import com.creelayer.activity.data.ActivityLogIdentity;
import com.creelayer.marketplace.crm.order.core.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ActivityLogIdentity(value = "uuid", type = Order.class)
public interface OrderViewDetail {

     UUID getUuid();

     String getCode();

     String getStatus();

     String getPayoutStatus();

     Delivery getDelivery();

     Payment getPayment();

     Invoice getInvoice();

     List<Payout> getPayouts();

     Customer getCustomer();

     Contact getContact();

     List<Item> getItems();

     PromoCode getPromoCode();

     LocalDateTime getCreatedAt();

     Summary getSummary();

     interface PromoCode {

         String getType();

         String getCode();

         long getDiscount();
    }

    interface Payout {

         long getAmount();

         String getDescription();
    }

    interface Payment {

         String getType();

         boolean getUseBalance();
    }

    interface Invoice {
         UUID getUuid();
    }

    interface Delivery {

         String getType();
         String getAddress();
    }

    interface Customer {

         UUID getUuid();

         String getName();

         String getPhone();

         String getEmail();
    }


    interface Contact {

         String getName();

         String getPhone();

         String getEmail();
    }

    interface Item {

         String getName();

         String getSku();

         String getImage();

         int getCount();

         long getAmount();

         long getTotal();

         long getDiscount();

         long getSummary();

         PromoCode getPromoCode();
    }

    interface Summary {

         long getTotal();

         long getItemsDiscount();

         long getItemsSummary();

         long getDiscount();

         long getSummary();
    }

}

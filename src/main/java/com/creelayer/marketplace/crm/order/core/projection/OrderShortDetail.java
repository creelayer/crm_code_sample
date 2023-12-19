package com.creelayer.marketplace.crm.order.core.projection;

import java.util.List;
import java.util.UUID;


public interface OrderShortDetail {

    UUID getUuid();

    List<Item> getItems();

    interface Item {

        String getName();

        String getSku();

        int getCount();

        int getTotal();
    }
}

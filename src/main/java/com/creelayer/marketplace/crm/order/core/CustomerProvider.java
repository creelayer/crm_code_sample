package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.model.OrderCustomer;

public interface CustomerProvider {

    OrderCustomer resolve(String identity);

    OrderCustomer identity(Contact contact);

    record Contact(String name, String phone, String email) {

    }
}

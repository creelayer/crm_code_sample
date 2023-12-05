package com.creelayer.marketplace.crm.order.core.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDelivery {

    public enum Type {
        NONE, NP
    }

    private Type type;

    private String address;
}
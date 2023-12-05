package com.creelayer.marketplace.crm.order.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.geo.Point;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AddOrderItemCommand {

    private UUID uuid;

    private String sku;

    private String name;

    private String url;

    private String image;

    private int amount;

    private int cashback;

    private int count;

    private String promoCode;

    private Point location;
}

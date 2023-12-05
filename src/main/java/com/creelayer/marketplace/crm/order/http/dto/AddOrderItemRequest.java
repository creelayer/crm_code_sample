package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.geo.Point;

public class AddOrderItemRequest {

    @Length(max = 50)
    @NotNull(message = "Item sku can't be null")
    @Pattern(regexp = "^[\\w\\d\\-]+$")
    public String sku;

    @Length(max = 255)
    @NotNull(message = "Item name can't be null")
    public String name;

    @Length(max = 255)
    @NotNull(message = "Item url can't be null")
    @Pattern(regexp = "^[a-z0-9\\-/.]+$")
    public String url;

    @Length(max = 255)
    @Pattern(regexp = "^[a-z0-9\\-/.]+$")
    public String image;

    @Min(100)
    public int amount;

    @Min(0)
    public int cashback;

    @Min(0)
    public int count;

    @Pattern(regexp = "^[a-zA-Z0-9]{5,10}$")
    public String promoCode;

    public Point location;
}

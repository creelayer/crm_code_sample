package com.creelayer.marketplace.crm.order.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    private String sku;

    private String name;

    private String url;

    private long amount;

    private int count;

    private long cashback;

    @Setter
    private String image;

    @Setter
    private OrderPromoCode promoCode;

    public OrderItem(String sku, String name, String url, long amount, int count) {

        Objects.requireNonNull(sku, "Item sku is required");
        Objects.requireNonNull(name, "Item name is required");
        Objects.requireNonNull(url, "Item url is required");


        if (amount <= 0)
            throw new IllegalStateException("Amount can't be less or equal zero");

        if (count <= 0)
            throw new IllegalStateException("Item count can't be less or equal zero");

        this.sku = sku;
        this.name = name;
        this.url = url;
        this.amount = amount;
        this.count = count;
    }


    public void setCashback(long cashback) {

        if (cashback < 0)
            throw new IllegalStateException("Cashback can't be less zero");

        this.cashback = cashback;
    }

    public OrderItem setCount(int count) {

        if (count <= 0)
            throw new IllegalStateException("Item count can't be less or equal zero");

        this.count = count;
        return this;
    }

    @JsonIgnore
    public long getTotal() {
        return amount * count;
    }

    @JsonIgnore
    public long getDiscount() {

        if (promoCode == null)
            return 0;

        if (promoCode.getType().equals(OrderPromoCode.TYPE.PERCENT))
            return getTotal() / 100 * promoCode.getDiscount();

        return promoCode.getDiscount() * 100;
    }

    @JsonIgnore
    public long getSummary() {
        long total = getTotal() - getDiscount();
        return total > 0 ? total : 0;
    }

    @Override
    public String toString() {
        return sku + ";" + count + ";" + amount + ";" + name;
    }
}

package com.creelayer.marketplace.crm.order.core.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderSummary {

    private final Order order;

    public long getTotal() {
        long result = 0;
        for (OrderItem e : order.getItems())
            result += e.getTotal();
        return result;
    }

    public long getItemsDiscount() {
        long result = 0;
        for (OrderItem e : order.getItems())
            result += e.getDiscount();
        return result;
    }

    public long getItemsSummary() {
        long result = 0;
        for (OrderItem e : order.getItems())
            result += e.getSummary();
        return result;
    }

    public long getDiscount() {

        if (order.getPromoCode() == null)
            return 0;

        if (order.getPromoCode().getType().equals(OrderPromoCode.TYPE.PERCENT))
            return getItemsSummary() / 100 * order.getPromoCode().getDiscount();

        return order.getPromoCode().getDiscount() * 100;
    }

    public long getSummary() {
        long total = getItemsSummary() - getDiscount();
        return total > 0 ? total : 0;
    }

}
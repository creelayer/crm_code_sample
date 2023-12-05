package com.creelayer.marketplace.crm.order.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@EqualsAndHashCode(of = {"code"})
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class OrderPromoCode {
    public enum TYPE {
        PERCENT, AMOUNT
    }

    private TYPE type;

    private String code;

    private long discount;

    public OrderPromoCode(TYPE type, String code, long discount) {

        Objects.requireNonNull(type, "Type is required");
        Objects.requireNonNull(code, "Code is required");

        if (discount <= 0)
            throw new IllegalStateException("Discount can't be less or equal zero");

        if (type.equals(TYPE.PERCENT) && discount > 100)
            throw new IllegalStateException("Discount percent can't be greatest 100% ");

        this.type = type;
        this.code = code;
        this.discount = discount;
    }
}

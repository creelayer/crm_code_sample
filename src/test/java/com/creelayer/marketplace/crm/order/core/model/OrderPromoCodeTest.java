package com.creelayer.marketplace.crm.order.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderPromoCodeTest {

    @Test
    void whenCreatePromoWithNegativeDiscount_thenExpectIllegalStateException(){
       assertThrows(IllegalStateException.class, () -> new OrderPromoCode(OrderPromoCode.TYPE.AMOUNT, "TEST", 0));
    }


    @Test
    void whenCreatePromoPresentWithMoreThenAllowDiscount_thenExpectIllegalStateException(){
        assertThrows(IllegalStateException.class,
                () -> new OrderPromoCode(OrderPromoCode.TYPE.PERCENT, "TEST", 1000));
    }

}
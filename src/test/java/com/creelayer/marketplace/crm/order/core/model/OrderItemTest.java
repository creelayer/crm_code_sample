package com.creelayer.marketplace.crm.order.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {


    @Test
    void whenCreateItemWithoutAmount_thenIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                0,
                1
        ));
    }

    @Test
    void whenCreateItemWithoutCount_thenIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                1000,
                0
        ));
    }

    @Test
    void whenCreateItemWithNegativeCashback_thenIllegalStateException() {
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                100,
                1
        );
        assertThrows(IllegalStateException.class, () -> item.setCashback(-1));
    }

    @Test
    void whenCreateWithoutPromo_thenDiscountIsZero() {
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                100,
                1
        );

        assertEquals(item.getDiscount(), 0);
    }

    @Test
    void whenCreateWithPromoPercent_thenDiscountIsPercentFromTotal() {
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                100,
                1
        );
        item.setPromoCode(new OrderPromoCode(OrderPromoCode.TYPE.PERCENT, "TEST", 10));
        assertEquals(item.getDiscount(), item.getTotal() * 0.1);
    }

    @Test
    void whenCreateWithPromoAmount_thenDiscountMultiple100() {
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                100,
                1
        );
        item.setPromoCode(new OrderPromoCode(OrderPromoCode.TYPE.AMOUNT, "TEST", 10));
        assertEquals(item.getDiscount(), 10 * 100);
    }


    @Test
    void whenCreateWithPromoAmountGreatestThanTotal_thenSummaryIsPositive() {
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                100,
                1
        );
        item.setPromoCode(new OrderPromoCode(OrderPromoCode.TYPE.AMOUNT, "TEST", 10000));
        assertTrue(item.getSummary() >= 0);
    }
}
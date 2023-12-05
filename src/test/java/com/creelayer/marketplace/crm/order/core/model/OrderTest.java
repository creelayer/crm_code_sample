package com.creelayer.marketplace.crm.order.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private OrderItem defaultOrderItem;
    private OrderCustomer defaultCustomer;
    private OrderContact defaultContact;

    @BeforeEach
    void init() {
        defaultCustomer = new OrderCustomer(UUID.randomUUID(), "Test name", "380636308315", "test@test.com");
        defaultContact = new OrderContact("Test name", "380636308315", "test@test.com", "Test comment");
        defaultOrderItem = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                14000,
                1
        );
    }

    @Test
    void whenOrderCreateWithInvalidContactData_thenExpectIllegalStateException() {

        Realm realm = new Realm(UUID.randomUUID());
        Set<OrderItem> items = new HashSet<>();
        items.add(defaultOrderItem);

        assertThrows(IllegalStateException.class, () -> new Order(
                realm,
                new OrderCustomer(UUID.randomUUID(), "Test name", "38063630831a", "test@test.com"),
                defaultContact,
                items
        ));

        assertThrows(IllegalStateException.class, () -> new Order(
                realm,
                new OrderCustomer(UUID.randomUUID(), "Test name", "380636308315", "testtest.com"),
                defaultContact,
                items
        ));

        assertThrows(IllegalStateException.class, () -> new Order(
                realm,
                defaultCustomer,
                new OrderContact("Test name", "38063630831a", "test@test.com", "Test comment"),
                items
        ));

        assertThrows(IllegalStateException.class, () -> new Order(
                realm,
                defaultCustomer,
                new OrderContact("Test name", "38063630831a", "testtest.com", "Test comment"),
                items
        ));
    }

    @Test
    void whenNotNewStateOrderChange_thenExpectIllegalStateException() {
        Realm realm = new Realm(UUID.randomUUID());
        OrderPayment payment = new OrderPayment(OrderPayment.Type.NONE, false);
        OrderInvoice invoice = new OrderInvoice(UUID.randomUUID());
        OrderPromoCode code = new OrderPromoCode(OrderPromoCode.TYPE.AMOUNT, "TEST", 100);
        Set<OrderItem> items = new HashSet<>();
        items.add(defaultOrderItem);

        Order order = new Order(realm, defaultCustomer, defaultContact, items);
        order.setStatus("SOME_OTHER_THEN_DEFAULT_STATUS");

        assertThrows(IllegalStateException.class, () -> order.addItem(defaultOrderItem));
        assertThrows(IllegalStateException.class, () -> order.updateItemCount("p124876", 10));
        assertThrows(IllegalStateException.class, () -> order.removeItem("p124876"));
        assertThrows(IllegalStateException.class, () -> order.setPayment(payment));
        assertThrows(IllegalStateException.class, () -> order.setInvoice(invoice));
        assertThrows(IllegalStateException.class, () -> order.addPromoCode(code));
        assertThrows(IllegalStateException.class, () -> order.addPromoCode("p124876", code));
    }

    @Test
    void whenOrderNoneExistItemChange_thenExpectNoSuchElementException() {
        Realm realm = new Realm(UUID.randomUUID());
        Set<OrderItem> items = new HashSet<>();
        items.add(defaultOrderItem);

        Order order = new Order(realm, defaultCustomer, defaultContact, items);
        assertThrows(NoSuchElementException.class, () -> order.updateItemCount("no_exist_sku", 10));
        assertThrows(NoSuchElementException.class, () -> order.removeItem("no_exist_sku"));
    }

    @Test
    void whenOrderItemWithCashbackChange_thenExpectPayoutStatusChange() {
        Realm realm = new Realm(UUID.randomUUID());

        defaultOrderItem.setCashback(20);
        Set<OrderItem> items = new HashSet<>();
        items.add(defaultOrderItem);

        Order order = new Order(realm, defaultCustomer, defaultContact, items);
        assertEquals(order.getPayoutStatus(), Order.PayoutStatus.REQUIRED);

        order.removeItem("p124876");
        assertEquals(order.getPayoutStatus(), Order.PayoutStatus.NONE);

        order.addItem(defaultOrderItem);
        assertEquals(order.getPayoutStatus(), Order.PayoutStatus.REQUIRED);
    }


    @Test
    void whenAddSameOrderItem_thenNoDuplicates() {
        Realm realm = new Realm(UUID.randomUUID());

        OrderItem firstItem = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                14000,
                2
        );

        OrderItem secondItem = new OrderItem(
                "p124876",
                "Компресор Vulkan IBL2070Y-100L",
                "pnevmooborudovanie/kompresor-vulkan-ibl2070y-100l-p908656.html",
                14000,
                2
        );

        Set<OrderItem> items = new HashSet<>();
        items.add(firstItem);
        items.add(secondItem);

        Order order = new Order(realm, defaultCustomer, defaultContact, items);
        assertEquals(1, order.getItems().size());
    }


    @Test
    void whenOrderItemWithCashback_thenExpectPayout() {
        Realm realm = new Realm(UUID.randomUUID());
        OrderItem item = new OrderItem(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                14000,
                2
        );
        item.setCashback(20);

        OrderItem item2 = new OrderItem(
                "p124877",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                14000,
                1
        );
        item2.setCashback(25);

        Set<OrderItem> items = new HashSet<>();
        items.add(item);
        items.add(item2);

        Order order = new Order(realm, defaultCustomer, defaultContact, items);
        assertEquals(2, order.getPayouts().size());
        assertEquals(65, order.getPayouts().stream().mapToLong(OrderPayout::getAmount).sum());
    }
}
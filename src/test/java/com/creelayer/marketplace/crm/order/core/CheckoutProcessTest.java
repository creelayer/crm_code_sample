package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.core.exception.CheckoutException;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.OrderCustomer;
import com.creelayer.marketplace.crm.order.core.model.OrderInvoice;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import com.creelayer.marketplace.crm.order.core.outgoing.CustomerProvider;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderInvoiceProvider;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutProcessTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerProvider customerProvider;

    @Mock
    private OrderInvoiceProvider invoiceProvider;

    @InjectMocks
    private CheckoutService checkoutProcess;

    private Realm realm;

    private CheckoutCommand.Contact contact;

    private CheckoutCommand.Payment payment;

    private CheckoutCommand.Delivery delivery;

    private Set<CheckoutCommand.Item> items;

    @BeforeEach
    void initCase() {
        realm = new Realm(UUID.randomUUID());

        contact = new CheckoutCommand.Contact("Test Test", "380630001122", "test@test.com", "test comment");
        payment = new CheckoutCommand.Payment(CheckoutCommand.Payment.Type.NONE);
        delivery = new CheckoutCommand.Delivery(CheckoutCommand.Delivery.Type.NONE, "Саксаганського, Київ, 02000");
        items = new HashSet<>();
        items.add(new CheckoutCommand.Item(
                "p124876",
                "Акумуляторний цвяхозабивач Makita XGT 40 V MAX FN001GA201",
                "elektro-instrument/akumuljatorniy-tsvjahozabivach-makita-xgt-40-v-max-fn001ga201-124876.html",
                14000,
                1
        ));
    }

    @Test
    void whenClientCheckout_thenRerunOrderWithCustomer() {
        whenOrderSaveReturnOrder();
        when(customerProvider.resolve(anyString())).thenAnswer(i -> new OrderCustomer(UUID.randomUUID(), "Test Test", i.getArgument(0), "test@test.com"));

        CheckoutCommand.Client client = new CheckoutCommand.Client("380630001123");
        CheckoutCommand command = new CheckoutCommand(realm.uuid, contact, payment, delivery, items);
        command.setClient(client);

        assertNotNull(checkoutProcess.checkout(command));

        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(argument.capture());

        assertNotNull(argument.getValue().getCustomer());
        assertNotNull(argument.getValue().getCustomer().getUuid());
        assertEquals(argument.getValue().getCustomer().getEmail().toString(), contact.getEmail());
        assertEquals(argument.getValue().getCustomer().getPhone().toString(), client.getIdentity());
        assertEquals(argument.getValue().getCustomer().getName(), contact.getName());
    }

    @Test
    void whenNoClientCheckout_thenRerunOrderWithCustomer() {
        whenOrderSaveReturnOrder();
        whenIdentClientReturnCustomer();

        CheckoutCommand command = new CheckoutCommand(realm.uuid, contact, payment, delivery, items);

        assertNotNull(checkoutProcess.checkout(command));

        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(argument.capture());

        assertNotNull(argument.getValue().getCustomer());
        assertNotNull(argument.getValue().getCustomer().getUuid());
        assertEquals(argument.getValue().getCustomer().getEmail().toString(), contact.getEmail());
        assertEquals(argument.getValue().getCustomer().getPhone().toString(), contact.getPhone());
        assertEquals(argument.getValue().getCustomer().getName(), contact.getName());
    }

    @Test
    void whenCheckoutWithInvoice_thenRerunOrderWithInvoice() {
        whenOrderSaveReturnOrder();
        whenIdentClientReturnCustomer();
        when(invoiceProvider.createInvoice(any(Order.class))).thenReturn(new OrderInvoice(UUID.randomUUID()));

        CheckoutCommand command = new CheckoutCommand(realm.uuid, contact, payment, delivery, items);
        command.setWithInvoice(true);

        assertNotNull(checkoutProcess.checkout(command));

        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(argument.capture());

        assertNotNull(argument.getValue().getInvoice());
        assertNotNull(argument.getValue().getInvoice().getUuid());
    }


    @Test
    void whenUnexpectedCheckoutException_thenExpectCheckoutException() {
        when(customerProvider.resolve(anyString())).thenThrow(NotFoundException.class);

        CheckoutCommand.Client client = new CheckoutCommand.Client("380630001123");
        CheckoutCommand command = new CheckoutCommand(realm.uuid, contact, payment, delivery, items);
        command.setClient(client);

        assertThrows(CheckoutException.class, () -> checkoutProcess.checkout(command));
    }


    void whenIdentClientReturnCustomer() {
        when(customerProvider.identity(any(CustomerProvider.Contact.class))).thenReturn(new OrderCustomer(UUID.randomUUID(), "Test Test", "380630001122", "test@test.com"));
    }

    void whenOrderSaveReturnOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> {
            Order order = i.getArgument(0);
            ReflectionTestUtils.setField(order, "uuid", UUID.randomUUID());
            ReflectionTestUtils.setField(order, "createdAt", LocalDateTime.now());
            return order;
        });
    }
}
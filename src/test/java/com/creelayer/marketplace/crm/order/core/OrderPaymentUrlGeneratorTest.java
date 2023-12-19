package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.order.core.model.*;
import com.creelayer.marketplace.crm.order.core.outgoing.CheckoutPaymentUrlProvider;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderPaymentUrlGeneratorTest {


    @Mock
    CheckoutPaymentUrlProvider paymentUrlProvider;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    CheckoutService checkoutService;

    UUID orderUuid;

    Order order;

    @BeforeEach
    public void init() {
        orderUuid = UUID.randomUUID();
        order = new Order(
                new Realm(UUID.randomUUID()),
                new OrderCustomer(UUID.randomUUID(), "Test", "380630001122", "test@tes.com"),
                new OrderContact("Test", "380630001122", "test@tes.com", "Test"),
                Collections.emptySet()
        );
        ReflectionTestUtils.setField(order, "uuid", orderUuid);
    }

    @Test
    void whenGeneratePaymentUrl_thenUrlReturned() {

        when(orderRepository.findById(orderUuid)).thenReturn(Optional.of(order));
        when(paymentUrlProvider.createExternalPaymentUrl(any(Order.class))).thenReturn("/test/url");

        String url = checkoutService.getExternalPaymentUrl(orderUuid);

        assertNotNull(url);
        verify(orderRepository, times(1)).findById(orderUuid);
        verify(paymentUrlProvider, times(1)).createExternalPaymentUrl(any(Order.class));
    }

    @Test
    void whenGenerateNotExistOrderPaymentUrl_thenUrlNotFoundException() {

        when(orderRepository.findById(any(UUID.class))).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> checkoutService.getExternalPaymentUrl(orderUuid));
        verify(orderRepository, times(1)).findById(orderUuid);
    }

}
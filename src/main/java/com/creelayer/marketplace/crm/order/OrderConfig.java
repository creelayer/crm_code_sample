package com.creelayer.marketplace.crm.order;

import com.creelayer.marketplace.crm.order.core.*;
import com.creelayer.marketplace.crm.order.core.outgoing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public CheckoutService checkoutService(
            OrderRepository orderRepository,
            CustomerProvider customerProvider,
            OrderInvoiceProvider invoiceProvider,
            CheckoutPromoCodeProvider promoCodeProvider,
            CheckoutPaymentUrlProvider paymentUrlProvider
    ) {
        return new CheckoutService(
                orderRepository,
                customerProvider,
                invoiceProvider,
                promoCodeProvider,
                paymentUrlProvider
        );
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, CheckoutPromoCodeProvider promoCodeProvider) {
        return new OrderService(orderRepository, promoCodeProvider);
    }
}

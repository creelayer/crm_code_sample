package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.core.exception.CheckoutException;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.support.OrderCompoundBuilder;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CheckoutService implements CheckoutProcess, OrderPaymentUrlGenerator {

    private final OrderRepository orderRepository;

    private final CustomerProvider customerProvider;

    private final OrderInvoiceProvider invoiceProvider;

    private final CheckoutPromoCodeProvider promoCodeProvider;

    private final CheckoutPaymentUrlProvider paymentUrlProvider;

    @Override
    public String getExternalPaymentUrl(UUID uuid) {
        Order order = orderRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Order not found"));
        return paymentUrlProvider.createExternalPaymentUrl(order);
    }

    @Override
    public Order checkout(Realm realm, CheckoutCommand command) {
        try {

            Order order = new OrderCompoundBuilder(customerProvider, promoCodeProvider, invoiceProvider)
                    .setWithInvoice(command.isWithInvoice())
                    .setRealm(realm)
                    .setClient(command.getClient())
                    .setContact(command.getContact())
                    .setPayment(command.getPayment())
                    .setDelivery(command.getDelivery())
                    .setItems(command.getItems())
                    .setPromoCode(command.getPromoCode())
                    .build();

            return orderRepository.save(order);

        } catch (Exception e) {
            throw new CheckoutException(e);
        }
    }
}

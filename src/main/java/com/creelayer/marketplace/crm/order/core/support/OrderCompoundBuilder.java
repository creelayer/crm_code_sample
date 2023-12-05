package com.creelayer.marketplace.crm.order.core.support;

import com.creelayer.marketplace.crm.order.core.CheckoutPromoCodeProvider;
import com.creelayer.marketplace.crm.order.core.CustomerProvider;
import com.creelayer.marketplace.crm.order.core.OrderInvoiceProvider;
import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.core.model.*;
import com.creelayer.marketplace.crm.order.core.model.OrderCustomer;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Setter
@RequiredArgsConstructor
public class OrderCompoundBuilder {


    private final CustomerProvider customerProvider;
    private final CheckoutPromoCodeProvider promoCodeProvider;
    private final OrderInvoiceProvider invoiceProvider;

    private Realm realm;
    private CheckoutCommand.Client client;
    private OrderContact contact;
    private Set<CheckoutCommand.Item> items;
    private OrderPayment payment;
    private OrderDelivery delivery;

    private boolean withInvoice;

    private String promoCode;

    public OrderCompoundBuilder setContact(CheckoutCommand.Contact contact) {

        Objects.requireNonNull(contact, "Contact cant be null");

        this.contact = new OrderContact(contact.getName(), contact.getPhone(), contact.getEmail(), contact.getComment());
        return this;
    }

    public OrderCompoundBuilder setPayment(CheckoutCommand.Payment payment) {
        this.payment = new OrderPayment(OrderPayment.Type.valueOf(payment.getType().name()), payment.isUseBalance());
        return this;
    }

    public OrderCompoundBuilder setDelivery(CheckoutCommand.Delivery delivery) {
        this.delivery = new OrderDelivery(OrderDelivery.Type.valueOf(delivery.getType().name()), delivery.getAddress());
        return this;
    }

    public Order build() {

        Objects.requireNonNull(realm, "RealmIdentity is required");
        Objects.requireNonNull(contact, "Customer contact is required");

        OrderCustomer customer = identityCustomer();

        Order order = new Order(realm, customer, getContacts(customer), mapItems(items));

        order.setDelivery(delivery);
        order.setPayment(payment);

        if (withInvoice)
            order.setInvoice(invoiceProvider.createInvoice(order));

        if (promoCode != null)
            order.addPromoCode(promoCodeProvider.resolve(promoCode));

        return order;
    }

    private OrderCustomer identityCustomer() {

        if (client != null)
            return customerProvider.resolve(client.getIdentity());

        return customerProvider.identity(new CustomerProvider.Contact(
                contact.getName(),
                contact.getPhone(),
                contact.getEmail()
        ));
    }

    private OrderContact getContacts(OrderCustomer customer) {

        if (contact != null)
            return contact;

        return new OrderContact(customer.getName(), customer.getPhone(), customer.getEmail(), "");
    }

    private Set<OrderItem> mapItems(Set<CheckoutCommand.Item> items) {
        Map<String, OrderPromoCode> codes = new HashMap<>();
        return items.stream().map(e -> {

            OrderItem item = new OrderItem(e.getSku(), e.getName(), e.getUrl(), e.getAmount(), e.getCount());
            item.setImage(e.getImage());
            item.setCashback(e.getCashback());

            if (e.getPromoCode() != null)
                item.setPromoCode(codes.computeIfAbsent(
                        e.getPromoCode(),
                        (code) -> promoCodeProvider.resolve(e.getPromoCode()))
                );

            return item;
        }).collect(Collectors.toSet());
    }
}
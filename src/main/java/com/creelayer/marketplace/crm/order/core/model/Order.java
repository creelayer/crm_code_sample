package com.creelayer.marketplace.crm.order.core.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.*;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends Aggregate<Order> {

    public static final String DEFAULT_STATUS = "NEW";

    public enum PayoutStatus {
        NONE, REQUIRED, COMPLETE
    }

    @Id
    @GeneratedValue
    private UUID uuid = UUID.randomUUID();

    /**
     * Side effect.
     * version need for recognize that is insert(persist) if id is custom, otherwise not all listener are works
     */
    @Version
    private Integer version;

    private final long code = System.currentTimeMillis() / 1000 % 10000;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @ManyToOne(optional = false)
    private OrderCustomer customer;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderContact contact;

    @Setter
    @Basic
    private String status = DEFAULT_STATUS;

    @Setter
    @Enumerated(EnumType.STRING)
    private PayoutStatus payoutStatus = PayoutStatus.NONE;

    @Setter
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderDelivery delivery;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderPayment payment;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderInvoice invoice;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Set<OrderItem> items = new HashSet<>();

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderPromoCode promoCode;

    public Order(@NonNull Realm realm, @NonNull OrderCustomer customer, @NonNull OrderContact contact, @NonNull Set<OrderItem> items) {

        this.realm = realm;
        this.customer = customer;
        this.contact = contact;
        this.items = items;

        this.payoutStatus = items.stream().anyMatch(e -> e.getCashback() > 0) ?
                PayoutStatus.REQUIRED : this.payoutStatus;
    }

    @PrePersist
    public void prePersist() {
        registerEvent(new OrderCreateEvent(this));
    }

    public OrderSummary getSummary() {
        return new OrderSummary(this);
    }

    public boolean isCardPaymentWithBalance() {
        return payment != null && payment.isUseBalance() && invoice != null;
    }

    public boolean isCardPayment() {
        return payment != null && invoice != null;
    }

    public void addItem(OrderItem item) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        if (item.getCashback() > 0)
            this.payoutStatus = PayoutStatus.REQUIRED;

        items.add(item);
    }

    public void updateItemCount(String sku, int count) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        if (count <= 0)
            throw new IllegalArgumentException("Count can't be les than one");

        OrderItem item = items.stream().filter(e -> e.getSku().equals(sku)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Not item exist"));

        item.setCount(count);
    }

    public void removeItem(String sku) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        if (!items.removeIf(e -> e.getSku().equals(sku)))
            throw new NoSuchElementException("Not item exist");

        payoutStatus = items.stream()
                .anyMatch(e -> e.getCashback() > 0) ? PayoutStatus.REQUIRED : PayoutStatus.NONE;
    }

    public List<OrderPayout> getPayouts() {

        List<OrderPayout> payouts = new ArrayList<>();

        for (OrderItem item : items)
            if (item.getCashback() > 0)
                payouts.add(new OrderPayout(item.getCashback() * item.getCount(), item.getCount() + " x " + item.getName()));

        return payouts;
    }

    public void setPayment(OrderPayment payment) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        this.payment = payment;
    }

    public void setInvoice(OrderInvoice invoice) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        this.invoice = invoice;
    }

    public void addPromoCode(OrderPromoCode code) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        promoCode = code;
    }

    public void addPromoCode(String sku, OrderPromoCode code) {

        if (!status.equals(DEFAULT_STATUS))
            throw new IllegalStateException("Can't modify order. Invalid order status");

        for (OrderItem orderItem : items)
            if (orderItem.getSku().equals(sku))
                orderItem.setPromoCode(code);
    }

    public Set<OrderPromoCode> getPromoCodes() {

        Set<OrderPromoCode> codes = new HashSet<>();

        if (promoCode != null)
            codes.add(promoCode);

        items.stream().filter(e -> e.getPromoCode() != null).forEach(e -> codes.add(e.getPromoCode()));

        return codes;
    }
}

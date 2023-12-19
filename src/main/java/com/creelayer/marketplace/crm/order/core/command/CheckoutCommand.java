package com.creelayer.marketplace.crm.order.core.command;

import com.creelayer.marketplace.crm.common.Default;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.Set;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CheckoutCommand {

    @NonNull
    private final UUID realm;

    @NonNull
    private final Contact contact;

    @NonNull
    private final Payment payment;

    @NonNull
    private final Delivery delivery;

    @NonNull
    private final Set<Item> items;

    @Setter
    private Client client;

    @Setter
    private boolean withInvoice;

    @Setter
    private String promoCode;


    @Getter
    @AllArgsConstructor
    public static final class Client {
        private String identity;
    }

    @Getter
    @AllArgsConstructor
    public static class Payment {

        public enum Type {
            NONE, CASH, CARD, TRANSFER
        }

        @NotNull(message = "Payment type can't be null")
        private final Type type;

        @Setter
        private boolean useBalance;

        @Default
        public Payment(Type type) {
            this.type = type;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Delivery {

        public enum Type {
            NONE, NP
        }

        private Type type;

        private String address;
    }

    @Getter
    @AllArgsConstructor
    public static class Contact {

        private String name;

        @NonNull
        private String phone;

        private String email;

        private String comment;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Item {

        @NonNull
        private final String sku;

        @NonNull
        private final String name;

        @NonNull
        private final String url;

        private final int amount;

        private final int count;

        @Setter
        private String image;

        @Setter
        private int cashback;

        @Setter
        private String promoCode;

        @Setter
        private Point location;
    }
}

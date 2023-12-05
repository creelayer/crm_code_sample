package com.creelayer.marketplace.crm.order.http.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.geo.Point;

import java.util.Set;

@Getter
public class CheckoutRequest {

    public boolean withInvoice;

    public @NotNull Set<@Valid Item> items;

    public @Valid @NotNull Contact contact;

    public @Valid @NotNull Payment payment;

    public @Valid @NotNull Delivery delivery;

    @Pattern(regexp = "^[a-zA-Z0-9]{5,10}$")
    public String promoCode;

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Payment {

        public enum Type {
            NONE, CASH, CARD, TRANSFER
        }

        @NotNull(message = "Payment type can't be null")
        public final Type type;

        @Setter
        public boolean useBalance;
    }

    @Getter
    @AllArgsConstructor
    public static class Delivery {

        public enum Type {
            NONE, NP
        }

        @NotNull(message = "Delivery type can't be null")
        public Type type;

        @NotNull(message = "Address can't be null")
        public String address;
    }

    @Getter
    @AllArgsConstructor
    public static class Contact {
        @Length(max = 255)
        public String name;

        @Length(max = 20)
        @NotEmpty(message = "Contact phone can't be null")
        @Pattern(regexp = "^[0-9]{12}$")
        public String phone;

        @Length(max = 255)
        @Email
        public String email;

        @Length(max = 5000)
        public String comment;
    }

    @Getter
    @AllArgsConstructor
    public static class Item {

        @Length(max = 50)
        @NotNull(message = "Item sku can't be null")
        @Pattern(regexp = "^[\\w\\d\\-]+$")
        public String sku;

        @Length(max = 255)
        @NotNull(message = "Item name can't be null")
        public String name;

        @Length(max = 255)
        @NotNull(message = "Item url can't be null")
        @Pattern(regexp = "^[a-z0-9\\-/.]+$")
        public String url;

        @Length(max = 255)
        @Pattern(regexp = "^[a-z0-9\\-/.]+$")
        public String image;

        @Min(100)
        public int amount;

        @Min(0)
        public int cashback;

        @Min(0)
        public int count;

        public String promoCode;

        public Point location;
    }
}

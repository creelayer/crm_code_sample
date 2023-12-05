package com.creelayer.marketplace.crm.order.core.exception;


public class CheckoutException extends RuntimeException {

    public CheckoutException() {
    }

    public CheckoutException(String message) {
        super(message);
    }

    public CheckoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckoutException(Throwable cause) {
        super(cause.getMessage(), cause);

    }
}

package com.creelayer.marketplace.crm.order.core.incoming;

import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;

import java.util.UUID;

public interface CheckoutProcess {
    UUID checkout(CheckoutCommand command);
}

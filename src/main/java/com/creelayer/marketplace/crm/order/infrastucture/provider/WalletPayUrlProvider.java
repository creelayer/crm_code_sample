package com.creelayer.marketplace.crm.order.infrastucture.provider;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.ClientIdentityService;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.model.ClientBalanceKey;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.CheckoutPaymentUrlProvider;
import com.creelayer.wallet.client.BalanceKey;
import com.creelayer.wallet.client.PaymentUrlGenerator;
import com.creelayer.wallet.client.dto.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class WalletPayUrlProvider implements CheckoutPaymentUrlProvider {

    private final PaymentUrlGenerator paymentUrlGenerator;

    private final ClientIdentityService identityService;

    private final RealmIdentityProvider realmProvider;

    @Override
    public String createExternalPaymentUrl(Order order) {

        if (order.isCardPayment())
            return paymentUrlGenerator.getUrl(new Invoice(order.getInvoice().getUuid()));

        if (order.isCardPaymentWithBalance()) {
            RealmIdentity identity = realmProvider.resolve();
            Client client = identityService
                    .getClient(new Realm(identity.getUuid()), order.getCustomer().getPhone());
            ClientBalanceKey key = client.getBalanceAccessKey();
            return paymentUrlGenerator.getUrl(
                    new BalanceKey(key.getUuid(), key.getSecret()),
                    new Invoice(order.getInvoice().getUuid())
            );
        }

        throw new IllegalStateException("Order can't be pay via external gateway");
    }
}

package com.creelayer.marketplace.crm.order.infrastucture.listener;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.promo.core.command.UsePromoCodeCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.OrderCreateEvent;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public final class OrderCreatePromoCodeUsesListener  {


    private final PromoCodeUsage promoUsage;

    private final RealmIdentityProvider realmProvider;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createManagerPermission(OrderCreateEvent event) {

        RealmIdentity identity = realmProvider.resolve();
        Order order = event.aggregate();

        order.getPromoCodes()
                .forEach(e -> promoUsage.use(new UsePromoCodeCommand(
                        identity.getUuid(),
                        order.getCustomer().getPhone().toString(),
                        e.getCode()
                )));
    }

}

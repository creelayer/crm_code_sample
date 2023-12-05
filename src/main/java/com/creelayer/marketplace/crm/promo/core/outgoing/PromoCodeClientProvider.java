package com.creelayer.marketplace.crm.promo.core.outgoing;

import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;

import java.util.UUID;


public interface PromoCodeClientProvider {

    PromoCodeClient resolve(UUID uuid);

    PromoCodeClient resolve(String phone);

}

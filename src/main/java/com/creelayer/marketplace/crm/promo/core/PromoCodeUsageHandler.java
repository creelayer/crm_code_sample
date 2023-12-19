package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.command.UsePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoException;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoNotFoundException;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsage;
import com.creelayer.marketplace.crm.promo.core.model.*;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeClientProvider;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeDetail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromoCodeUsageHandler implements PromoCodeUsage {

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeClientProvider clientProvider;

    @Override
    public PromoCodeDetail review(Realm realm, String code) {

        PromoCode promo = promoCodeRepository.findPromoCode(realm, code)
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        if (!promo.isUtilizable())
            throw new PromoException("Can't use this promo");

        return new PromoCodeDetail(promo);
    }

    @Override
    public void use(UsePromoCodeCommand command) {

        PromoCode promo = promoCodeRepository.findPromoCode(new Realm(command.getRealm()), command.getCode())
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        PromoCodeClient client = clientProvider.resolve(command.getPhone());

        promo.addUse(client);
        promoCodeRepository.save(promo);
    }
}

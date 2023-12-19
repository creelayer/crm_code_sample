package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoException;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.support.PromoConditionMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
public class PromoCodeGenerator {


    private final PromoCodeRepository promoCodeRepository;

    private final PromoConditionMapper mapper = new PromoConditionMapper();

    @Setter
    private int codeLength = 5;

    @Setter
    private static int maxFindIterations = 5;

    @Setter
    private String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public Codes generate(PromoGroup group, GeneratePromoCodeCommand command) {

        Codes codes = new Codes();

        for (int i = 0; i < command.getCount(); i++)
            codes.add(new PromoCode(
                    group,
                    PromoCode.Status.valueOf(command.getStatus().name()),
                    command.getName(),
                    command.getCode() != null ? command.getCode() : findNexCode(group.getRealm()),
                    PromoCode.TYPE.valueOf(command.getType().name()),
                    command.getDiscount(),
                    command.getExpiredAt(),
                    command.getMaxUses(),
                    mapper.map(command.getConditions())
            ));

        return codes;
    }

    private String findNexCode(Realm realm) {

        for (int j = 0; j < maxFindIterations; j++) {
            String code = generate(codeLength);
            if (!promoCodeRepository.existsPromoCode(realm, code))
                return code;
        }

        throw new PromoException("Can't find next code");
    }

    private String generate(int length) {

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }
        return salt.toString().toUpperCase();
    }

    public static class Codes extends ArrayList<PromoCode> {
        public void setOwner(PromoCodeClient client) {
            this.forEach(e -> e.setOwner(client));
        }
    }
}

package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.exeption.PromoException;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class PromoCodeGenerator {


    private final PromoCodeRepository promoCodeRepository;

    @Setter
    private int codeLength = 5;

    @Setter
    private static int maxFindIterations = 5;

    @Setter
    private String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public List<String> generate(Realm realm, int count) {
        List<String> codes = new ArrayList<>();

        for (int i = 0; i < count; i++)
            codes.add(findNexCode(realm));

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
}

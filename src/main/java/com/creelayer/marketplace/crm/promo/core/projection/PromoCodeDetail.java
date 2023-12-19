package com.creelayer.marketplace.crm.promo.core.projection;

import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromoCodeDetail {

    public String code;

    public long discount;

    public String type;

    public List<Condition> conditions;

    public PromoCodeDetail(PromoCode promo) {

        code = promo.getCode();
        type = promo.getType().name();
        discount = promo.getDiscount();

        conditions = promo.getConditions().stream()
                .map(e -> new Condition(e.getPath(), e.getPriceFrom(), e.getPriceTo(), e.getSkus(), e.isInvert()))
                .toList();
    }

    @AllArgsConstructor
    public static class Condition {

        public String path;

        public Long priceFrom;

        public Long priceTo;

        public List<String> skus;

        public boolean invert;
    }
}

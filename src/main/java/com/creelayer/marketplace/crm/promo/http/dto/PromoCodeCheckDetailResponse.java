package com.creelayer.marketplace.crm.promo.http.dto;

import com.creelayer.marketplace.crm.promo.core.model.PromoCode;

import java.util.List;

public class PromoCodeCheckDetailResponse {

    public String code;

    public long discount;

    public PromoCode.TYPE type;

    public List<Condition> conditions;


    public static class Condition {

        public String path;

        public  Long priceFrom;

        public Long priceTo;

        public  List<String> skus;

    }

}

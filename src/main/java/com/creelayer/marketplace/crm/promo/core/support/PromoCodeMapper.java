package com.creelayer.marketplace.crm.promo.core.support;

import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;

import java.util.List;
import java.util.stream.Collectors;

public class PromoCodeMapper extends PromoConditionMapper {

    public List<PromoCode> map(GeneratePromoCodeCommand command, PromoGroup group, List<String> codes) {
        return codes.stream().map(code -> map(command, group, null, code)).collect(Collectors.toList());
    }

    public List<PromoCode> map(GeneratePromoCodeCommand command, PromoGroup group, PromoCodeClient owner, List<String> codes) {
        return codes.stream().map(code -> map(command, group, owner, code)).collect(Collectors.toList());
    }

    private PromoCode map(GeneratePromoCodeCommand command, PromoGroup group, PromoCodeClient owner, String code){
        return new PromoCode(
                group,
                owner,
                PromoCode.Status.valueOf(command.getStatus().name()),
                command.getName(),
                code,
                PromoCode.TYPE.valueOf(command.getType().name()),
                command.getDiscount(),
                command.getExpiredAt(),
                command.getMaxUses()
        );
    }
}

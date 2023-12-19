package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeManage;
import com.creelayer.marketplace.crm.promo.core.model.*;
import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoException;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoNotFoundException;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeClientProvider;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.support.PromoConditionMapper;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PromoCodeService implements PromoCodeManage {

    private final PromoGroupRepository promoGroupRepository;

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeClientProvider clientProvider;

    private final PromoCodeGenerator generator;

    private final PromoConditionMapper conditionMapper = new PromoConditionMapper();

    @Override
    public void generate(GeneratePromoCodeCommand command) {

        PromoGroup group = promoGroupRepository.findById(command.getGroup())
                .orElseThrow(() -> new PromoNotFoundException("Promo group not found"));

        if (command.getCode() != null && promoCodeRepository.existsPromoCode(group.getRealm(), command.getCode()))
            throw new PromoException("Code already exist");

        PromoCodeGenerator.Codes codes = generator.generate(group, command);

        if (command.getOwner() != null)
            codes.setOwner(clientProvider.resolve(command.getOwner()));

        promoCodeRepository.save(codes);
    }

    @Override
    public void update(UpdatePromoCodeCommand command) {

        PromoCode code = promoCodeRepository.findById(command.getCode())
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        code.setName(command.getName())
                .setExpiredAt(command.getExpiredAt())
                .setStatus(PromoCode.Status.valueOf(command.getStatus().name()));

        promoCodeRepository.save(code);
    }

    @Override
    public PromoCode update(ManagePromoConditionCommand command) {

        PromoCode code = promoCodeRepository.findById(command.getEntity())
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        code.setConditions(conditionMapper.map(command.getConditions()));

        return promoCodeRepository.save(code);
    }

    @Override
    public void remove(UUID uuid) {
        PromoCode code = promoCodeRepository.findById(uuid)
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        promoCodeRepository.delete(code);
    }
}

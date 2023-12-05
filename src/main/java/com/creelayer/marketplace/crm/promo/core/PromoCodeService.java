package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.command.UsePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeManage;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsage;
import com.creelayer.marketplace.crm.promo.core.model.*;
import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoException;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoNotFoundException;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeClientProvider;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeUsagesRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.support.PromoCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PromoCodeService implements PromoCodeManage, PromoCodeUsage {

    private final PromoGroupRepository promoGroupRepository;

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeUsagesRepository usagesRepository;

    private final PromoCodeClientProvider clientProvider;

    private final PromoCodeGenerator generator;

    private final PromoCodeMapper codeMapper = new PromoCodeMapper();

    @Override
    public PromoCode getCode(Realm realm, String code) {

        PromoCode promoCode = promoCodeRepository.findPromoCode(realm, code)
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        if (!promoCode.isActive())
            throw new PromoException("Promo code is not active");

        if (promoCode.getMaxUses() != null && usagesRepository.countUsages(promoCode) >= promoCode.getMaxUses())
            throw new PromoException("Max promo code usages");

        return promoCode;
    }

    @Override
    public void useCode(Realm realm, UsePromoCodeCommand command) {
        PromoCode promoCode = getCode(realm, command.getCode());
        PromoCodeClient bearer = clientProvider.resolve(command.getPhone());
        usagesRepository.save(new PromoCodeUses(bearer, promoCode));
    }

    @Override
    public void generate(GeneratePromoCodeCommand command) {

        PromoGroup group = promoGroupRepository.findById(command.getGroup())
                .orElseThrow(() -> new PromoNotFoundException("Promo group not found"));

        if (command.getCode() != null && promoCodeRepository.existsPromoCode(group.getRealm(), command.getCode()))
            throw new PromoException("Code already exist");

        List<String> codes = generator.generate(group.getRealm(), command.getCount());

        if (command.getOwner() != null) {
            PromoCodeClient owner = clientProvider.resolve(command.getOwner());
            promoCodeRepository.save(codeMapper.map(command, group, owner, codes));
        } else {
            promoCodeRepository.save(codeMapper.map(command, group, codes));
        }
    }

    @Override
    public PromoCode update(UpdatePromoCodeCommand command) {

        PromoCode code = promoCodeRepository.findById(command.getCode())
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        code
                .setName(command.getName())
                .setExpiredAt(command.getExpiredAt())
                .setStatus(PromoCode.Status.valueOf(command.getStatus().name()));

        return promoCodeRepository.save(code);
    }

    @Override
    public PromoCode update(ManagePromoConditionCommand command) {

        PromoCode code = promoCodeRepository.findById(command.getUuid())
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        code.setConditions(codeMapper.map(command.getConditions()));

        return promoCodeRepository.save(code);
    }

    @Override
    public void remove(UUID uuid) {
        PromoCode code = promoCodeRepository.findById(uuid)
                .orElseThrow(() -> new PromoNotFoundException("Promo code not found"));

        promoCodeRepository.delete(code);
    }
}

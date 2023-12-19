package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoNotFoundException;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoActionManage;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoActionRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.support.PromoActionCommandMapper;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PromoActionService implements PromoActionManage {

    private final PromoActionRepository promoActionRepository;

    private final PromoGroupRepository promoGroupRepository;

    private final PromoActionCommandMapper mapper = new PromoActionCommandMapper();

    @Override
    public UUID create(CreatePromoActionCommand command) {
        PromoGroup group = promoGroupRepository.findById(command.getGroup())
                .orElseThrow(() -> new PromoNotFoundException("Promo group not found"));
        PromoAction action = mapper.map(group, command);
        return promoActionRepository.save(action).getUuid();
    }

    @Override
    public void update(UpdatePromoActionCommand command) {
        PromoAction action = promoActionRepository.findById(command.getAction())
                .orElseThrow(() -> new PromoNotFoundException("Promo action not found"));
        promoActionRepository.save(mapper.map(command, action));
    }

    @Override
    public PromoAction update(ManagePromoConditionCommand command) {

        PromoAction action = promoActionRepository.findById(command.getEntity())
                .orElseThrow(() -> new PromoNotFoundException("Promo action not found"));

        action.setConditions(mapper.map(command.getConditions()));

        return promoActionRepository.save(action);
    }

    @Override
    public void remove(UUID uuid) {
        PromoAction action = promoActionRepository
                .findById(uuid).orElseThrow(() -> new PromoNotFoundException("Action not found"));

        promoActionRepository.delete(action);
    }
}

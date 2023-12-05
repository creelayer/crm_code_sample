package com.creelayer.marketplace.crm.promo.core;

import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupManage;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.command.CreatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.exeption.PromoNotFoundException;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PromoGroupService implements PromoGroupManage {

    private final PromoGroupRepository groupRepository;

    @Override
    public PromoGroup create(Realm realm, CreatePromoGroupCommand command) {

        PromoGroup group = new PromoGroup(realm, command.getName());

        return groupRepository.save(group);
    }

    @Override
    public PromoGroup update(UpdatePromoGroupCommand command) {

        PromoGroup group = groupRepository.findById(command.getUuid())
                .orElseThrow(() -> new PromoNotFoundException("Group not found"));

        group.setName(command.name).setStatus(PromoGroup.Status.valueOf(command.status.name()));

        return groupRepository.save(group);
    }

    @Override
    public void remove(UUID uuid) {
        PromoGroup group = groupRepository.findById(uuid)
                .orElseThrow(() -> new PromoNotFoundException("Group not found"));

        groupRepository.delete(group);
    }
}

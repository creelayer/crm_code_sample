package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ManagePromoConditionCommand {

    private UUID entity;

    private List<Condition> conditions;

}

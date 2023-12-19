package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter
@AllArgsConstructor
public class UpdatePromoGroupCommand {

    public enum Status {
        ACTIVE, DISABLED
    }

    private UUID group;

    public Status status;

    public String name;

}

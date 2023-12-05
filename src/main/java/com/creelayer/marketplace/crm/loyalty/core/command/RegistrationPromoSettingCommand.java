package com.creelayer.marketplace.crm.loyalty.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationPromoSettingCommand {

    private UUID realm;

    private Integer discount;

    private Integer validity;
}

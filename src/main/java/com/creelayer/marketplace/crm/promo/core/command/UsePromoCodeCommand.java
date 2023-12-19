package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UsePromoCodeCommand {

    private UUID realm;

    private String phone;

    private String code;

}

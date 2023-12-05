package com.creelayer.marketplace.crm.promo.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdatePromoCodeCommand {

    public enum Status {
        ACTIVE, DISABLED
    }

    private UUID code;

    private String name;

    private LocalDateTime expiredAt;

    private Status status;

}

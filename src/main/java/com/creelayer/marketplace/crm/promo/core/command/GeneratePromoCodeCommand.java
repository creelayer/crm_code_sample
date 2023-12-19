package com.creelayer.marketplace.crm.promo.core.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class GeneratePromoCodeCommand {

    public enum Status {
        ACTIVE, DISABLED
    }

    public enum TYPE {
        PERCENT, AMOUNT
    }

    private final UUID group;

    private UUID owner;

    @Builder.Default
    private int count = 1;

    private final String name;

    private final String code;

    private final TYPE type;

    private final long discount;

    private final LocalDateTime expiredAt;

    @Builder.Default
    private  Status status = Status.ACTIVE;

    private Integer maxUses = 1;

    @Builder.Default
    private List<Condition> conditions = new ArrayList<>();

    public GeneratePromoCodeCommand(UUID group, UUID owner, int count, String name, String code, TYPE type, long discount, LocalDateTime expiredAt, Status status, Integer maxUses, List<Condition> conditions) {

        if (code == null && count <= 0)
            throw new IllegalStateException("Invalid code generate iterations");

        if (code != null && count > 1)
            throw new IllegalStateException("Max code generate for custom code is one");

        this.group = group;
        this.owner = owner;
        this.count = count;
        this.name = name;
        this.code = code;
        this.type = type;
        this.discount = discount;
        this.expiredAt = expiredAt;
        this.status = status;
        this.maxUses = maxUses;
        this.conditions = conditions;
    }
}

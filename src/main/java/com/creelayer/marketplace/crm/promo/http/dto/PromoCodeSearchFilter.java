package com.creelayer.marketplace.crm.promo.http.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PromoCodeSearchFilter {

    public enum State {
        ACTIVE, USED, ARCHIVED
    }

    public enum Status {
        ACTIVE, DISABLED
    }

    public UUID client;

    @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "Invalid search", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public String search;

    public UUID group;

    public Status status;

    public State state;
}

package com.creelayer.marketplace.crm.secutiry;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Editable {

    enum Action {
        IS_EDITABLE, IS_DELETABLE
    }

    @JsonIgnore
    default boolean isEditable() {
        return true;
    }

    @JsonIgnore
    default boolean isDeletable() {
        return true;
    }
}

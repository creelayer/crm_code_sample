package com.creelayer.marketplace.crm.secutiry;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Identity {
    @JsonIgnore
    String getId();
}

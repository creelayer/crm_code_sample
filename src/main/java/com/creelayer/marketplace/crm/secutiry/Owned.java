package com.creelayer.marketplace.crm.secutiry;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.Principal;

public interface Owned {
    @JsonIgnore
    Principal getPrincipal();
}

package com.creelayer.marketplace.crm.secutiry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode
public class ResourceGrantedAuthority implements GrantedAuthority {

    @Getter
    private final String resource;

    private final String scope;

    public ResourceGrantedAuthority(String resource, String scope) {
        this.resource = resource;
        this.scope = scope;
    }

    @Override
    public String getAuthority() {
        return scope;
    }
}

package com.creelayer.marketplace.crm.secutiry;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class ResourceOwner implements Owned, Identity {

    private String ownerId;

    private String resourceId;

    @Override
    public String getId() {
        return resourceId;
    }

    @Override
    public Principal getPrincipal() {
        return () -> ownerId;
    }
}

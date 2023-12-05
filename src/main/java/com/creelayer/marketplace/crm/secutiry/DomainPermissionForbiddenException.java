package com.creelayer.marketplace.crm.secutiry;


import lombok.Getter;
import lombok.Setter;

public class DomainPermissionForbiddenException extends RuntimeException {

    @Getter
    private final Identity identity;

    @Getter
    @Setter
    private String permission;

    public DomainPermissionForbiddenException(String message, Identity identity) {
        super(message);
        this.identity = identity;
    }

    public DomainPermissionForbiddenException(String message, Identity identity, String permission) {
        super(message);
        this.identity = identity;
        this.permission = permission;
    }
}
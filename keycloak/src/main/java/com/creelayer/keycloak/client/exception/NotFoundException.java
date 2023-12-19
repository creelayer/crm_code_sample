package com.creelayer.keycloak.client.exception;

public class NotFoundException extends ResourceException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}

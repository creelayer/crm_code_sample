package com.creelayer.keycloak.client.exception;

public class ConflictException extends ResourceException {
    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}

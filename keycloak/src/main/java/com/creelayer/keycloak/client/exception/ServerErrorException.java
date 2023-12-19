package com.creelayer.keycloak.client.exception;

public class ServerErrorException extends ResourceException {
    public ServerErrorException() {
    }

    public ServerErrorException(String message) {
        super(message);
    }
}

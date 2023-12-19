package com.creelayer.keycloak.client;

import com.creelayer.keycloak.client.exception.ConflictException;
import com.creelayer.keycloak.client.exception.NotFoundException;
import com.creelayer.keycloak.client.exception.ServerErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

public class ApiErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.equals(HttpStatus.CONFLICT))
            return new ConflictException("Resource exist");

        if (responseStatus.equals(HttpStatus.NOT_FOUND))
            return new NotFoundException("Resource not found");

        return new ServerErrorException("Can't process request");
    }

}
package com.creelayer.keycloak.client;

import com.creelayer.keycloak.client.config.ApiConfiguration;
import com.creelayer.keycloak.client.dto.Attributes;
import com.creelayer.keycloak.client.dto.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(name = "keycloak.user.incoming", url = "${spring.security.oauth2.client.provider.keycloak.admin-uri}", configuration = ApiConfiguration.class)
public interface UserApi {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{uuid}")
    UserRepresentation view(@PathVariable("uuid") UUID uuid);

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{uuid}")
    void attributes(@PathVariable("uuid") UUID uuid, @RequestBody Attributes attributes);
}

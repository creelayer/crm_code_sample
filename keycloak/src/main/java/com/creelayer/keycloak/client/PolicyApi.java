package com.creelayer.keycloak.client;

import com.creelayer.keycloak.client.config.ResourceApiConfiguration;
import com.creelayer.keycloak.client.dto.Permission;
import com.creelayer.keycloak.client.dto.PermissionView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "keycloak.policy.incoming", url = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}", configuration = ResourceApiConfiguration.class)
public interface PolicyApi {

    @RequestMapping(method = RequestMethod.GET, value = "/authz/protection/uma-policy/{uuid}")
    PermissionView get(@PathVariable("uuid") UUID uuid);

    @RequestMapping(method = RequestMethod.GET, value = "/authz/protection/uma-policy")
    List<PermissionView> get(@RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/authz/protection/uma-policy/{resource}")
    PermissionView create(@PathVariable("resource") UUID resource, @RequestBody Permission permission);

    @RequestMapping(method = RequestMethod.PUT, value = "/authz/protection/uma-policy/{uuid}")
    void update(@PathVariable("uuid") UUID uuid, @RequestBody Permission permission);

    @RequestMapping(method = RequestMethod.DELETE, value = "/authz/protection/uma-policy/{uuid}")
    void delete(@PathVariable("uuid") UUID uuid);

}

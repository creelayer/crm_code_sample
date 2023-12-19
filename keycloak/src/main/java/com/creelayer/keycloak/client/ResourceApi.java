package com.creelayer.keycloak.client;

import com.creelayer.keycloak.client.config.ResourceApiConfiguration;
import com.creelayer.keycloak.client.dto.Resource;
import com.creelayer.keycloak.client.dto.ResourceView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "keycloak.resource.incoming", url = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}", configuration = ResourceApiConfiguration.class)
public interface ResourceApi {

    @RequestMapping(method = RequestMethod.GET, value = "/authz/protection/resource_set/{uuid}")
    ResourceView get(@PathVariable("uuid") UUID uuid);

    @RequestMapping(method = RequestMethod.GET, value = "/authz/protection/resource_set")
    List<ResourceView> get(@RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/authz/protection/resource_set")
    ResourceView create(@RequestBody Resource resource);

    @RequestMapping(method = RequestMethod.PUT, value = "/authz/protection/resource_set/{uuid}")
    void update(@PathVariable("uuid") UUID uuid, @RequestBody Resource resource);

    @RequestMapping(method = RequestMethod.DELETE, value = "/authz/protection/resource_set/{uuid}")
    void delete(@PathVariable("uuid") UUID uuid);
}

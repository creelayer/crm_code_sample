package com.creelayer.keycloak.client.config;

import com.creelayer.keycloak.client.PolicyApi;
import com.creelayer.keycloak.client.ResourceApi;
import com.creelayer.keycloak.client.ResourceManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResourceManager.class)
    ResourceManager resourceManager(ResourceApi resourceApi, PolicyApi policyApi){
        return new ResourceManager(resourceApi, policyApi);
    }
}

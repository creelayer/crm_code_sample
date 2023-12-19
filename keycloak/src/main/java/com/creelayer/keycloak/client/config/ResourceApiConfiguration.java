package com.creelayer.keycloak.client.config;

import com.creelayer.keycloak.client.ApiErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class ResourceApiConfiguration {

    public static final String CLIENT_REGISTRATION_ID = "resource";

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public ResourceApiConfiguration(OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
                                    ClientRegistrationRepository clientRegistrationRepository) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        OAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);

        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(CLIENT_REGISTRATION_ID)
                .principal("notPrincipal")
                .build();

        return requestTemplate -> {
            OAuth2AuthorizedClient client = authorizedClientManager.authorize(oAuth2AuthorizeRequest);
            if (client != null)
                requestTemplate.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ApiErrorDecoder();
    }

}

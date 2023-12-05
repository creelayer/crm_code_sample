package com.creelayer.marketplace.crm.app.config;

import com.creelayer.keycloak.client.config.KeycloakClientConfiguration;
import com.creelayer.marketplace.crm.app.RealmConvertor;
import com.creelayer.marketplace.crm.common.reaml.Realm;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.common.ForbiddenException;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.marketplace.crm.promo.infrastucture.convertor.PhoneToPromoClientConverter;
import com.creelayer.marketplace.crm.client.infrastucture.convertor.PhoneToClientConverter;
import com.creelayer.setting.client.SettingsConfig;
import com.creelayer.wallet.client.config.WalletConfig;
import jakarta.annotation.PostConstruct;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@Configuration
@EnableFeignClients(basePackages = {"com.creelayer.mosquito.client", "com.creelayer.keycloak.client", "com.creelayer.wallet.client"})
@Import({WalletConfig.class, SettingsConfig.class, KeycloakClientConfiguration.class})
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private ClientIdentity clientIdentity;

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RealmConvertor());
        registry.addConverter(new PhoneToClientConverter(clientIdentity, realmIdentityProvider()));
        registry.addConverter(new PhoneToPromoClientConverter(clientIdentity, realmIdentityProvider()));
    }

    @Bean
    public SettingApi<Setting> marketSettings(CouchDbConnector settingsCouchDbConnector) {
        return new SettingApi<>(Setting.class, settingsCouchDbConnector, "_design/market");
    }

    @Bean
    public RealmIdentityProvider realmIdentityProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getPrincipal() instanceof Market market)
                return new Realm(market.getUuid());

            throw new ForbiddenException("Invalid realm provided");
        };
    }
}

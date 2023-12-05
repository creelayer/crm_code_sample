package com.creelayer.marketplace.crm.loyalty;

import com.creelayer.marketplace.crm.loyalty.core.LoyaltySettingService;
import com.creelayer.marketplace.crm.loyalty.core.outgoing.LoyaltySettingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoyaltyConfig {
    @Bean
    LoyaltySettingService loyaltySettingService(LoyaltySettingRepository settingRepository) {
        return new LoyaltySettingService(settingRepository);
    }
}

package com.creelayer.marketplace.crm.promo;

import com.creelayer.marketplace.crm.promo.core.*;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoActionRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromoConfig {

    @Bean
    PromoCodeGenerator promoCodeGenerator(PromoCodeRepository promoCodeRepository){
        return new PromoCodeGenerator(promoCodeRepository);
    }

    @Bean
    PromoGroupService promoGroupService(PromoGroupRepository groupRepository){
        return new PromoGroupService(groupRepository);
    }

    @Bean
    PromoActionService promoActionService(PromoActionRepository promoActionRepository, PromoGroupRepository promoGroupRepository){
        return new PromoActionService(promoActionRepository, promoGroupRepository);
    }

}

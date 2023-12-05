package com.creelayer.marketplace.crm.market;

import com.creelayer.marketplace.crm.market.core.ManagerService;
import com.creelayer.marketplace.crm.market.core.MarketService;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerPermissionProvider;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerRepository;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketConfig {

    @Bean
    public MarketService marketService(MarketRepository marketRepository){
       return new MarketService(marketRepository);
    }

    @Bean
    public ManagerService managerService(ManagerRepository managerRepository, MarketRepository marketRepository, ManagerPermissionProvider permissionProvider) {
        return new ManagerService(managerRepository, marketRepository, permissionProvider);
    }

}

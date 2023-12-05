package com.creelayer.marketplace.crm.client;


import com.creelayer.marketplace.crm.client.core.ClientDetailService;
import com.creelayer.marketplace.crm.client.core.ClientIdentityService;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.ClientBalanceService;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.client.core.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public ClientIdentityService clientIdentityService(ClientRepository clientRepository) {
        return new ClientIdentityService(clientRepository);
    }

    @Bean
    public ClientDetailService clientDetailService(ClientIdentity clientIdentity, ClientBalanceProvider balanceProvider){
        return new ClientDetailService(clientIdentity, balanceProvider);
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientService(clientRepository);
    }

    @Bean
    public ClientBalanceService clientBalanceService(ClientBalanceProvider balanceProvider, ClientRepository clientRepository) {
        return new ClientBalanceService(balanceProvider, clientRepository);
    }
}

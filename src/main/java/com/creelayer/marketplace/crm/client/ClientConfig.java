package com.creelayer.marketplace.crm.client;


import com.creelayer.marketplace.crm.client.core.ClientDetailHandler;
import com.creelayer.marketplace.crm.client.core.ClientIdentityService;
import com.creelayer.marketplace.crm.client.core.incoming.ClientIdentity;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.ClientBalanceHandler;
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
    public ClientDetailHandler clientDetailHandler(ClientRepository clientRepository, ClientBalanceProvider balanceProvider){
        return new ClientDetailHandler(clientRepository, balanceProvider);
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientService(clientRepository);
    }

    @Bean
    public ClientBalanceHandler clientBalanceHandler(ClientBalanceProvider balanceProvider, ClientRepository clientRepository) {
        return new ClientBalanceHandler(balanceProvider, clientRepository);
    }
}

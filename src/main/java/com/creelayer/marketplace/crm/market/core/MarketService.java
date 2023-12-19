package com.creelayer.marketplace.crm.market.core;

import com.creelayer.marketplace.crm.market.core.command.CreateMarketCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.exception.MarketNotfoundException;
import com.creelayer.marketplace.crm.market.core.incoming.MarketManage;
import com.creelayer.marketplace.crm.market.core.model.Account;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.market.core.model.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@RequiredArgsConstructor
public class MarketService implements UserDetailsService, MarketManage {

    private final MarketRepository marketRepository;

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        return marketRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UsernameNotFoundException("Market not found"));
    }

    public UUID create(CreateMarketCommand command) {

        Account account = new Account(command.getAccount());
        Market market = new Market(account, command.getName())
                .setUrl(command.getUrl())
                .setPhone(command.getPhone())
                .setEmail(command.getEmail());

        market.addManager(new Manager(account, market));
        return marketRepository.save(market).getUuid();
    }

    @Override
    public void update(UpdateMarketCommand command) {
        Market market = marketRepository.findById(command.getMarket())
                .orElseThrow(() -> new MarketNotfoundException("market not found"));

        market.setUrl(command.getUrl())
                .setPhone(command.getPhone())
                .setEmail(command.getEmail());

        marketRepository.save(market);
    }

    public void remove(UUID uuid) {

        Market market = marketRepository.findById(uuid)
                .orElseThrow(() -> new MarketNotfoundException("market not found"));

        marketRepository.delete(market);
    }
}

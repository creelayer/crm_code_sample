package com.creelayer.marketplace.crm.market.core.outgoing;

import com.creelayer.marketplace.crm.market.core.model.Account;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.model.Market;

import java.util.Optional;
import java.util.UUID;


public interface ManagerRepository {

    boolean existsByMarketAndAccount(Market market, Account account);

    Optional<Manager> findById(UUID uuid);

    Manager save(Manager manager);

    void delete(Manager manager);

}

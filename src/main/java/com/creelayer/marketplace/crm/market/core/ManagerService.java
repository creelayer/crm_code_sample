package com.creelayer.marketplace.crm.market.core;

import com.creelayer.marketplace.crm.market.core.command.CreateManagerCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdatePermissionCommand;
import com.creelayer.marketplace.crm.market.core.exception.ManagerNotFoundException;
import com.creelayer.marketplace.crm.market.core.exception.MarketNotfoundException;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerDetailDistributor;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerManage;
import com.creelayer.marketplace.crm.market.core.model.Account;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerPermissionProvider;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerRepository;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.market.core.exception.ManagerException;
import com.creelayer.marketplace.crm.market.core.projection.ManagerView;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ManagerService implements ManagerManage, ManagerDetailDistributor {

    private final ManagerRepository managerRepository;

    private final MarketRepository marketRepository;

    private final ManagerPermissionProvider permissionProvider;

    public ManagerView getManager(UUID uuid) {
        Manager manager = managerRepository.findById(uuid)
                .orElseThrow(() -> new ManagerNotFoundException("Manager not found"));

        List<String> permissions = permissionProvider.getPermission(manager);

        return new ManagerView(manager, permissions);
    }

    public Manager addManager(CreateManagerCommand command) {

        Account account = new Account(command.getAccount());
        Market market = marketRepository.findById(command.getMarket())
                .orElseThrow(() -> new MarketNotfoundException("Market not found"));

        if (managerRepository.existsByMarketAndAccount(market, account))
            throw new ManagerException("Manager already exist");

        return managerRepository.save(new Manager(account, market));
    }

    public void updatePermissions(UpdatePermissionCommand command) {

        Manager manager = managerRepository.findById(command.getManager())
                .orElseThrow(() -> new ManagerNotFoundException("Manager not found"));

        permissionProvider.updatePermissions(manager, command.getScopes());
    }

    public void remove(UUID uuid) {

        Manager manager = managerRepository.findById(uuid)
                .orElseThrow(() -> new ManagerNotFoundException("Manager not found"));

        managerRepository.delete(manager);
    }
}

package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.command.CreateManagerCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdatePermissionCommand;

import java.util.UUID;

public interface ManagerManage {

    UUID addManager(CreateManagerCommand command);

    void updatePermissions(UpdatePermissionCommand command);

    void remove(UUID uuid);

}

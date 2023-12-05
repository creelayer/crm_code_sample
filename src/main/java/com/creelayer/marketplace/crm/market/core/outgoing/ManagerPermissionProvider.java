package com.creelayer.marketplace.crm.market.core.outgoing;

import com.creelayer.marketplace.crm.market.core.model.Manager;

import java.util.List;

public interface ManagerPermissionProvider {
    List<String> getPermission(Manager manager);

    void updatePermissions(Manager manager, List<String> scopes);
}

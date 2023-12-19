package com.creelayer.marketplace.crm.market.core.projection;

import com.creelayer.marketplace.crm.market.core.model.Manager;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ManagerView {

    private final UUID uuid;

    private final List<String> permissions;

    public ManagerView(Manager manager, List<String> permissions) {
        this.uuid = manager.getUuid();
        this.permissions = permissions;
    }
}

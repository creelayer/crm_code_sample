package com.creelayer.marketplace.crm.market.infrastucture.provider;

import com.creelayer.keycloak.client.ResourceManager;
import com.creelayer.keycloak.client.dto.Permission;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerPermissionProvider;
import com.creelayer.keycloak.client.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeycloakManagerPermissionProvider implements ManagerPermissionProvider {

    private final ResourceManager resourceManager;

    @Override
    public List<String> getPermission(Manager manager) {
        return resourceManager
                .getPermission("market:" + manager.getMarket().getUuid() + ":user:" + manager.getAccount().getUuid())
                .scopes;
    }

    @Override
    public void updatePermissions(Manager manager, List<String> scopes) {
        try {

            String permissionName = "market:" + manager.getMarket().getUuid() + ":user:" + manager.getAccount().getUuid();

            if (scopes.isEmpty()) {
                resourceManager.removePermission(permissionName);
                return;
            }

            Permission permission = new Permission();
            permission.name = permissionName;
            permission.description = "Market account manage permission";
            permission.scopes.addAll(scopes);
            permission.users.add(manager.getAccount().getUuid());
            resourceManager.upsert(manager.getMarket().getUuid(), permission);
        } catch (NotFoundException e) {

        }
    }
}

package com.creelayer.marketplace.crm.market.infrastucture.listener;

import com.creelayer.keycloak.client.ResourceManager;
import com.creelayer.keycloak.client.dto.Permission;
import com.creelayer.keycloak.client.dto.Resource;
import com.creelayer.keycloak.client.exception.ConflictException;
import com.creelayer.keycloak.client.exception.NotFoundException;
import com.creelayer.marketplace.crm.market.core.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Component
public final class ManagerResourcePermissionListener {

    private final ResourceManager resourceManager;

    private static final List<String> MARKET_SCOPES = List.of(
            "order_read",
            "order_manage",
            "client_read",
            "client_manage",
            "import_manage",
            "import_source_manage",
            "import_upload",
            "currency_manage",
            "product_read",
            "product_manage",
            "promo_code_read",
            "promo_code_manage",
            "loyalty_manage",
            "menu_manage",
            "catalog_manage",
            "category_manage",
            "brand_manage",
            "tag_manage",
            "vocabulary_manage",
            "page_manage",
            "page_delete",
            "banner_manage",
            "setting_manage"
    );

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(ManagerCreateEvent event) {
        Manager manager = event.aggregate();
        createUMAResources(manager.getMarket());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(ManagerRemoveEvent event) {
        Manager manager = event.aggregate();
        removeResourcePermissions(manager);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createManagerPermission(ManagerPermissionEvent event) {
        Manager manager = event.aggregate();
//        createResourcePermissions(manager);
    }

    private void createUMAResources(Market market) {
        try {
            Resource resource = new Resource();
            resource.type = "marketplace:market";
            resource.id = market.getUuid();
            resource.name = market.getUuid().toString();
            resource.displayName = market.getName() + " market";
            resource.scopes.addAll(MARKET_SCOPES);
            resourceManager.create(resource);
        } catch (ConflictException e) {
        }
    }

//    private void createResourcePermissions(Manager manager) {
//
//        try {
//            List<String> scopes = manager.getScopes();
//            String permissionName = "market:" + manager.getMarket().getUuid() + ":user:" + manager.getAccount().getUuid();
//
//            if (scopes.isEmpty()) {
//                resourceManager.removePermission(permissionName);
//                return;
//            }
//
//            Permission permission = new Permission();
//            permission.name = permissionName;
//            permission.description = "Market account manage permission";
//            permission.scopes.addAll(scopes);
//            permission.users.add(manager.getAccount().getUuid());
//            resourceManager.upsert(manager.getMarket().getUuid(), permission);
//        } catch (NotFoundException e) {
//            System.out.println("A");
//        }
//
//    }

    private void removeResourcePermissions(Manager manager) {
        try {
            String permissionName = "market:" + manager.getMarket().getUuid() + ":user:" + manager.getAccount().getUuid();
            resourceManager.removePermission(permissionName);
        } catch (NotFoundException e) {
        }
    }
}

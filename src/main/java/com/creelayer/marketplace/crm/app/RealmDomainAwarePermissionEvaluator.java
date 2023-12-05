package com.creelayer.marketplace.crm.app;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerSearch;
import com.creelayer.marketplace.crm.market.core.projection.ManagerDetail;
import com.creelayer.marketplace.crm.secutiry.DomainAwarePermissionEvaluator;
import com.creelayer.marketplace.crm.secutiry.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RealmDomainAwarePermissionEvaluator extends DomainAwarePermissionEvaluator {

    private final ManagerSearch managerSearch;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (targetDomainObject instanceof RealmIdentity realm) {

            ManagerDetail manager = managerSearch.
                    getDetail(UUID.fromString(authentication.getName()), realm.getUuid())
                    .orElseThrow(() -> new NotFoundException("Invalid manager"));

            if (permission.equals("session"))
                return manager.isActive();

            ResourceOwner resource = new ResourceOwner(manager.getMarket().getAccount().getUuid().toString(), realm.getUuid().toString());
            return super.hasPermission(authentication, resource, permission);
        }

        return super.hasPermission(authentication, targetDomainObject, permission);
    }
}

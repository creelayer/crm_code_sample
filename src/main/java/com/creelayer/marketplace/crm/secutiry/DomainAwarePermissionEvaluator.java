package com.creelayer.marketplace.crm.secutiry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class DomainAwarePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (isProtected(targetDomainObject, permission))
            return false;

        if (isOwned(authentication, targetDomainObject))
            return true;

        return checkDomainAccess(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    protected boolean isOwned(Authentication authentication, Object targetDomainObject) {

        if (targetDomainObject instanceof Owned owned)
            return owned.getPrincipal().getName().equals(authentication.getName());

        return false;
    }

    protected boolean checkDomainAccess(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Identity identity) {
            boolean accepted = authentication.getAuthorities().contains(new ResourceGrantedAuthority(identity.getId(), (String) permission));

            if (!accepted)
                throw new DomainPermissionForbiddenException("Invalid domain permission", identity, (String) permission);

            return true;
        }

        return false;
    }

    protected boolean isProtected(Object targetDomainObject, Object permission) {

        if (targetDomainObject instanceof Editable editable) {

            if (Editable.Action.IS_EDITABLE.name().equals(permission) && !editable.isEditable())
                return true;

            return Editable.Action.IS_DELETABLE.name().equals(permission) && !editable.isDeletable();
        }

        return false;
    }
}
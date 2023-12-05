package com.creelayer.marketplace.crm.secutiry;

import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class CompoundDomainAwarePermissionEvaluator implements PermissionEvaluator {

    private final List<PermissionEvaluator> permissionEvaluators;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        for (PermissionEvaluator evaluator : permissionEvaluators){
            if(evaluator.hasPermission(authentication, targetDomainObject, permission))
                return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        for (PermissionEvaluator evaluator : permissionEvaluators){
            if(evaluator.hasPermission(authentication, targetId, targetType, permission))
                return true;
        }
        return false;
    }
}

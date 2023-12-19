package com.creelayer.marketplace.crm.app;

import com.creelayer.marketplace.crm.common.ForbiddenException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.secutiry.DomainAwarePermissionEvaluator;
import com.creelayer.marketplace.crm.secutiry.ResourceOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RealmDomainAwarePermissionEvaluator extends DomainAwarePermissionEvaluator {

    private static final String REALM_PERMISSION_QUERY = "SELECT m.account.uuid as aid, m.market.uuid as rid, m.status as status FROM Manager m WHERE m.market.uuid = :realm AND m.account.uuid = :account";

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof RealmIdentity realm) {
            try {
                Tuple result = em.createQuery(REALM_PERMISSION_QUERY, Tuple.class)
                        .setParameter("account", UUID.fromString(authentication.getName()))
                        .setParameter("realm", realm.getUuid())
                        .getSingleResult();

                if (permission.equals("session"))
                    return result.get("status").equals(Manager.Status.ACTIVE);

                ResourceOwner resource = new ResourceOwner(result.get("aid").toString(), result.get("rid").toString());
                return super.hasPermission(authentication, resource, permission);
            } catch (NoResultException e) {
                throw new ForbiddenException("Invalid access");
            }
        }
        return super.hasPermission(authentication, targetDomainObject, permission);
    }
}

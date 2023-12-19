package com.creelayer.marketplace.crm.app;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.secutiry.DomainAwarePermissionEvaluator;
import com.creelayer.marketplace.crm.secutiry.ResourceOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


@Component
public class RequestPermissionEvaluator extends DomainAwarePermissionEvaluator {

    private static final Map<String, String> queries = Map.of(
            "Market", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m WHERE m.uuid = :uuid",
            "Manager", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.market.uuid AND t.uuid = :uuid",
            "Client", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.realm.uuid AND t.uuid = :uuid",
            "Order", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.realm.uuid AND t.uuid = :uuid",
            "PromoGroup", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.realm.uuid AND t.uuid = :uuid",
            "PromoCode", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.group.realm.uuid AND t.uuid = :uuid",
            "PromoAction", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.group.realm.uuid AND t.uuid = :uuid",
            "Vocabulary", "SELECT m.account.uuid as aid, m.uuid as rid FROM Market m, %s t WHERE m.uuid = t.group.realm.uuid AND t.uuid = :uuid"
    );

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        if (!queries.containsKey(targetType))
            return super.hasPermission(authentication, targetId, targetType, permission);

        try {
            UUID uuid = (UUID) targetId;
            Tuple result = em.createQuery(String.format(queries.get(targetType), targetType), Tuple.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();

            ResourceOwner resource = new ResourceOwner(result.get("aid").toString(), result.get("rid").toString());
            return super.hasPermission(authentication, resource, permission);
        } catch (NoResultException e) {
            throw new NotFoundException("Entity not found");
        }
    }
}

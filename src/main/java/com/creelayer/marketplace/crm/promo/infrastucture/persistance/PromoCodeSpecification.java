package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.model.PromoCodeUses;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class PromoCodeSpecification implements Specification<PromoCode> {

    public Realm realm;

    public PromoCodeSearchQuery query;

    @Override
    public Predicate toPredicate(Root<PromoCode> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        Join<PromoGroup, PromoCode> groups = root.join("group");
        predicates.add(cb.equal(groups.get("realm"), realm));
        predicates.add(cb.isNull(root.get("deletedAt")));

        if (query.getGroup() != null)
            predicates.add(cb.equal(root.get("group").get("uuid"), query.getGroup()));

        if (query.getSearch() != null)
            predicates.add(cb.like(root.get("code"), query.getSearch().toUpperCase() + "%"));

        if (query.getStatus() != null)
            predicates.add(cb.equal(root.get("status"), PromoCode.Status.valueOf(query.getStatus().name())));

        if (query.getClient() != null) {

            predicates.add(cb.equal(root.get("owner").get("uuid"), query.getClient()));

            if (query.getState() != null && query.getState().equals(PromoCodeSearchQuery.State.ACTIVE)) {
                Join<PromoCodeUses, PromoCode> usages = root.join("uses", JoinType.LEFT);
                predicates.add(cb.equal(root.get("status"), PromoCode.Status.ACTIVE));
                predicates.add(cb.greaterThan(root.get("expiredAt"), LocalDateTime.now()));
                predicates.add(cb.isNull(usages));
            }

            if (query.getState() != null && query.getState().equals(PromoCodeSearchQuery.State.USED)) {
                Join<PromoCodeUses, PromoCode> usages = root.join("uses");
                predicates.add(cb.equal(usages.get("client").get("uuid"), query.getClient()));
            }

            if (query.getState() != null && query.getState().equals(PromoCodeSearchQuery.State.ARCHIVED)) {
                Join<PromoCodeUses, PromoCode> usages = root.join("uses", JoinType.LEFT);
                predicates.add(cb.lessThan(root.get("expiredAt"), LocalDateTime.now()));
                predicates.add(cb.isNull(usages));
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class PromoGroupSpecification implements Specification<PromoGroup> {

    public Realm realm;

    public PromoGroupSearchQuery query;

    @Override
    public Predicate toPredicate(Root<PromoGroup> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("realm"), realm));
        predicates.add(cb.isNull(root.get("deletedAt")));

        if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getSearch().toLowerCase() + "%"));


        if (query.getStatus() != null)
            predicates.add(cb.equal(root.get("status"), PromoGroup.Status.valueOf(query.getStatus().name())));


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

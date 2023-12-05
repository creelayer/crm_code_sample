package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class PromoActionSpecification implements Specification<PromoAction> {

    public Realm realm;

    public PromoActionSearchQuery query;

    @Override
    public Predicate toPredicate(Root<PromoAction> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        Join<PromoGroup, PromoCode> groups = root.join("group");

        predicates.add(cb.equal(groups.get("realm"), realm));

        if (query.getGroup() != null)
            predicates.add(cb.equal(root.get("group").get("uuid"), query.getGroup()));

        if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getSearch().toLowerCase() + "%"));


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

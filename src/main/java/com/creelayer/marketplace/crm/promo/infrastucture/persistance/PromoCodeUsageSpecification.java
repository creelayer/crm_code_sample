package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.model.PromoCodeUses;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeUsageSearchQuery;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class PromoCodeUsageSpecification implements Specification<PromoCodeUses> {

    public Realm realm;

    public PromoCodeUsageSearchQuery query;

    @Override
    public Predicate toPredicate(Root<PromoCodeUses> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        Join<PromoCode, PromoCodeUses> code = root.join("code");
        Join<PromoGroup, PromoCode> group = code.join("group");
        predicates.add(cb.equal(group.get("realm"), realm));

        if (query.getCode() != null)
            predicates.add(cb.equal(code.get("uuid"), query.getCode()));

        if (query.getSearch() != null)
            predicates.add(cb.like(code.get("code"), query.getSearch().toUpperCase() + "%"));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

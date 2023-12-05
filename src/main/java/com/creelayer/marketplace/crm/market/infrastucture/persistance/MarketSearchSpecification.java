package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class MarketSearchSpecification implements Specification<Market> {

    private MarketSearchQuery query;

    @Override
    public Predicate toPredicate(Root<Market> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("account").get("uuid"), query.getAccount()));

        if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getSearch().toLowerCase() + "%"));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ManagerSearchSpecification implements Specification<Manager> {

    private ManagerSearchQuery query;

    @Override
    public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("market").get("uuid"), this.query.getMarket()));

        if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + query.getSearch().toLowerCase() + "%"));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

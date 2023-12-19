package com.creelayer.marketplace.crm.client.infrastucture.persistance;

import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ClientSearchSpecification implements Specification<Client> {

    public Realm realm;

    public ClientSearchQuery query;

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("realm"), this.realm));

        if (query.isPhone())
            predicates.add(cb.like(cb.lower(root.get("phone")), "%" + query.getSearch().toLowerCase() + "%"));

        else if (query.isEmail())
            predicates.add(cb.equal(root.get("email"), query.getSearch().toLowerCase()));

        else if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getSearch().toLowerCase() + "%"));


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

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
            predicates.add(cb.equal(root.get("phone"), query.search));

        else if (query.isEmail())
            predicates.add(cb.equal(root.get("email"), query.search.toLowerCase()));

        else if (query.search != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.search.toLowerCase() + "%"));


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

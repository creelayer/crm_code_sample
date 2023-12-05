package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class OrderSearchSpecification implements Specification<Order> {

    public RealmIdentity realm;

    public OrderSearchQuery query;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("realm").get("uuid"), realm.getUuid()));

        if (query.getStatus() != null)
            predicates.add(root.get("status").in(query.getStatus()));

        if (query.getCustomer() != null)
            predicates.add(cb.equal(root.get("customer").get("uuid"), query.getCustomer()));

        if (query.isCode())
            predicates.add(cb.equal(root.get("code"), query.getSearch()));

        else if (query.isPhone())
            predicates.add(cb.equal(cb.function("jsonb_extract_path_text", String.class, root.get("contact"), cb.literal("phone")), query.getSearch()));

        else if (query.isEmail())
            predicates.add(cb.equal(cb.function("jsonb_extract_path_text", String.class, root.get("contact"), cb.literal("email")), query.getSearch().toLowerCase()));

        else if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(cb.function("jsonb_extract_path_text", String.class, root.get("contact"), cb.literal("name"))), "%" + query.getSearch().toLowerCase() + "%"));

        if (query.getFrom() != null && query.getTo() != null)
            predicates.add(cb.between(root.get("createdAt"), query.getFrom().atStartOfDay(), query.getTo().atStartOfDay()));

        if (query.getPayout() != null)
            predicates.add(cb.equal(root.get("payoutStatus"), Order.PayoutStatus.valueOf(query.getPayout().name())));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

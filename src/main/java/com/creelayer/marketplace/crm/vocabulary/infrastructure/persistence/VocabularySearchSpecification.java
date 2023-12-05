package com.creelayer.marketplace.crm.vocabulary.infrastructure.persistence;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.vocabulary.core.querty.VocabularySearchQuery;
import com.creelayer.marketplace.crm.vocabulary.core.model.Vocabulary;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class VocabularySearchSpecification implements Specification<Vocabulary> {

    public RealmIdentity realm;

    public VocabularySearchQuery query;

    @Override
    public Predicate toPredicate(Root<Vocabulary> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Set<Predicate> predicates = new HashSet<>();

        predicates.add(cb.equal(root.get("realm").get("uuid"), this.realm.getUuid()));

        if (query.getSearch() != null)
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getSearch().toLowerCase() + "%"));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

package com.creelayer.marketplace.crm.common.jpaSpecificationProjection;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Repository;


import java.beans.PropertyDescriptor;
import java.util.*;


@Repository
public  class JpaSpecificationProjectionExecutorImpl<T> implements  JpaSpecificationProjectionExecutor<T> {

    @PersistenceContext
    private EntityManager em;

    private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    public <P> Page<P> findAll(Specification<T> specification, Pageable pageable, Class<P> projectionClass) {
        Class<?> entityClass =  GenericTypeResolver.resolveTypeArgument(specification.getClass(), Specification.class);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
        Root<?> root = tupleQuery.from(entityClass);

        Set<Selection<?>> selections = new HashSet<>();
        List<PropertyDescriptor> inputProperties = projectionFactory.getProjectionInformation(projectionClass).getInputProperties();
        for (PropertyDescriptor propertyDescriptor : inputProperties) {
            String property = propertyDescriptor.getName();
            PropertyPath path = PropertyPath.from(property, entityClass);
            selections.add(QueryUtils.toExpressionRecursively(root, path).alias(property));
        }


        tupleQuery.multiselect(new ArrayList<>(selections))
                .where(specification.toPredicate((Root) root, tupleQuery, criteriaBuilder))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, criteriaBuilder));

        TypedQuery<Tuple> query = em.createQuery(tupleQuery)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        List<Tuple> results = query.getResultList();


        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> countRoot = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(countRoot))
                .where(specification.toPredicate((Root) countRoot, countQuery, criteriaBuilder));

        Long totalCount = em.createQuery(countQuery).getSingleResult();


        // Create maps for each result tuple
        List<P> mappedResults = new ArrayList<>(results.size());
        for (Tuple tuple : results) {
            Map<String, Object> mappedResult = new HashMap<>(tuple.getElements().size());
            for (TupleElement<?> element : tuple.getElements()) {
                String name = element.getAlias();
                mappedResult.put(name, tuple.get(name));
            }

            mappedResults.add(projectionFactory.createProjection(projectionClass, mappedResult));
        }

        return new PageImpl<>(mappedResults, pageable, totalCount);
    }

}

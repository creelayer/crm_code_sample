package com.creelayer.marketplace.crm.common.jpaSpecificationProjection;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface JpaSpecificationProjectionExecutor<T> {
    public <P> Page<P> findAll(Specification<T> specification, Pageable pageable, Class<P> projectionClass);
}

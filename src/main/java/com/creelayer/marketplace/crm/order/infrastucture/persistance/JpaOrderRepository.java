package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.OrderRepository;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationProjectionExecutor<Order>, JpaOrderAnalytics, OrderRepository {

    @Override
    default <T> Page<T> search(RealmIdentity realm, OrderSearchQuery query, Pageable pageable, Class<T> projection) {
        return findAll(new OrderSearchSpecification(realm, query), pageable, projection);
    }

}

package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.reaml.Realm;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.projection.OrderSearchResult;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderSearch extends
        org.springframework.data.repository.Repository<Order, UUID>,
        JpaSpecificationProjectionExecutor<Order>,
        QueryHandler<OrderSearchQuery, Page<OrderSearchResult>> {

    @Override
    default Page<OrderSearchResult> ask(OrderSearchQuery query){
        return findAll(
                new OrderSearchSpecification(new Realm(query.realm), query),
                PageRequest.of(query.page, query.size, Sort.by(Sort.Direction.DESC, "createdAt")),
                OrderSearchResult.class
        );
    }
}

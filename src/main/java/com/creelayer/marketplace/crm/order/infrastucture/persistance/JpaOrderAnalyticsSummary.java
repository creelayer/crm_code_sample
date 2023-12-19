package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.projection.OrderSummaryAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticSummaryQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderAnalyticsSummary extends
        org.springframework.data.repository.Repository<Order, UUID>,
        QueryHandler<OrderAnalyticSummaryQuery, OrderSummaryAnalytic> {


    String DAY_COUNT_SQL = "SELECT COUNT(*) " +
            "FROM orders o " +
            "WHERE o.realm = :#{#query.realm} AND o.created_at BETWEEN current_date AND current_date + interval '1 day' ";
    String NEW_COUNT_SQL = "SELECT COUNT(*) " +
            "FROM orders o " +
            "WHERE o.realm = :#{#query.realm} AND o.status = 'NEW' ";

    @Query(value = "SELECT (" + DAY_COUNT_SQL + ") as dayOrdersCount, (" + NEW_COUNT_SQL + ") as newOrderCount", nativeQuery = true)
    @Override
    OrderSummaryAnalytic ask(OrderAnalyticSummaryQuery query);
}

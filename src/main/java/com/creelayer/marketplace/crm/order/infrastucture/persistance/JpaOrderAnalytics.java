package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.projection.OrderDateAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaOrderAnalytics extends
        org.springframework.data.repository.Repository<Order, UUID>,
        QueryHandler<OrderAnalyticQuery, List<OrderDateAnalytic>> {

    @Override
    default List<OrderDateAnalytic> ask(OrderAnalyticQuery query){
        return switch (query.period) {
            case DAY -> getAggregateDayAnalytic(query);
            case WEEK -> getAggregateWeekAnalytic(query);
            case MONTH -> getAggregateMonthAnalytic(query);
        };
    }

    @Query(value = "SELECT COUNT(*) as count, date_trunc('day', o.created_at) as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#query.realm} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('day', o.created_at) ORDER BY date_trunc('day', o.created_at)", nativeQuery = true)
    List<OrderDateAnalytic> getAggregateDayAnalytic(OrderAnalyticQuery query);

    @Query(value = "SELECT COUNT(*) as count, date_trunc('week', o.created_at)\\:\\:date || '-' ||  (date_trunc('week', o.created_at) + interval '6 days')\\:\\:date as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#query.realm} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('week', o.created_at) ORDER BY date_trunc('week', o.created_at)", nativeQuery = true)
    List<OrderDateAnalytic> getAggregateWeekAnalytic(OrderAnalyticQuery query);

    @Query(value = "SELECT COUNT(*) as count, date_trunc('month', o.created_at)\\:\\:date || '-' ||  (date_trunc('month', o.created_at) + interval '1 month')\\:\\:date as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#query.realm} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('month', o.created_at) ORDER BY date_trunc('month', o.created_at)", nativeQuery = true)
    List<OrderDateAnalytic> getAggregateMonthAnalytic(OrderAnalyticQuery query);
}

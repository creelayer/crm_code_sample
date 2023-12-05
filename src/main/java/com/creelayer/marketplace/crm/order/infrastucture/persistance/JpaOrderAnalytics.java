package com.creelayer.marketplace.crm.order.infrastucture.persistance;

import com.creelayer.marketplace.crm.order.core.projection.OrderDateAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticSearchQuery;
import com.creelayer.marketplace.crm.order.core.OrderAnalytics;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaOrderAnalytics extends OrderAnalytics {

    @Query(value = "SELECT COUNT(*) as count " +
            "FROM orders o " +
            "WHERE o.realm = :#{#realm.uuid} AND o.created_at BETWEEN current_date AND current_date + interval '1 day' ", nativeQuery = true)
    @Override
    int getDayOrdersCount(Realm realm);


    @Query(value = "SELECT COUNT(*) as count " +
            "FROM orders o " +
            "WHERE o.realm = :#{#realm.uuid} AND o.status = 'NEW' ", nativeQuery = true)
    @Override
    int getNewOrderCount(Realm realm);

    @Query(value = "SELECT COUNT(*) as count, date_trunc('day', o.created_at) as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#realm.uuid} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('day', o.created_at) ORDER BY date_trunc('day', o.created_at)", nativeQuery = true)
    @Override
    List<OrderDateAnalytic> getAggregateDayAnalytic(Realm realm, OrderAnalyticSearchQuery query);

    @Query(value = "SELECT COUNT(*) as count, date_trunc('week', o.created_at)\\:\\:date || '-' ||  (date_trunc('week', o.created_at) + interval '6 days')\\:\\:date as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#realm.uuid} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('week', o.created_at) ORDER BY date_trunc('week', o.created_at)", nativeQuery = true)
    @Override
    List<OrderDateAnalytic> getAggregateWeekAnalytic(Realm realm, OrderAnalyticSearchQuery query);

    @Query(value = "SELECT COUNT(*) as count, date_trunc('month', o.created_at)\\:\\:date || '-' ||  (date_trunc('month', o.created_at) + interval '1 month')\\:\\:date as date " +
            "FROM orders o " +
            "WHERE o.realm = :#{#realm.uuid} AND o.created_at BETWEEN :#{#query.from} AND :#{#query.to} " +
            "GROUP BY date_trunc('month', o.created_at) ORDER BY date_trunc('month', o.created_at)", nativeQuery = true)
    @Override
    List<OrderDateAnalytic> getAggregateMonthAnalytic(Realm realm, OrderAnalyticSearchQuery query);
}

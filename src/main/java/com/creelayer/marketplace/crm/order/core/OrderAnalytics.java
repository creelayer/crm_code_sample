package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.order.core.projection.OrderDateAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticSearchQuery;
import com.creelayer.marketplace.crm.order.core.model.Realm;

import java.util.List;

public interface OrderAnalytics {

    int getDayOrdersCount(Realm realm);

    int getNewOrderCount(Realm realm);

    List<OrderDateAnalytic> getAggregateDayAnalytic(Realm realm, OrderAnalyticSearchQuery query);

    List<OrderDateAnalytic> getAggregateWeekAnalytic(Realm realm, OrderAnalyticSearchQuery query);

    List<OrderDateAnalytic> getAggregateMonthAnalytic(Realm realm, OrderAnalyticSearchQuery query);

}

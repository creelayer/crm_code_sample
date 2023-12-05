package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import com.creelayer.marketplace.crm.order.core.projection.OrderDateAnalytic;
import com.creelayer.marketplace.crm.order.core.projection.OrderSummaryAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticSearchQuery;
import com.creelayer.marketplace.crm.order.http.dto.OrderAnalyticSearchFilter;
import com.creelayer.marketplace.crm.order.core.OrderAnalytics;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("order/analytics")
public class AnalyticsController {

    private OrderAnalytics analytics;

    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("")
    public List<OrderDateAnalytic> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid OrderAnalyticSearchFilter filter
    ) {

        OrderAnalyticSearchQuery query = new OrderAnalyticSearchQuery(filter.from, filter.to);

        return switch (filter.period) {
            case DAY -> analytics.getAggregateDayAnalytic(new Realm(realm.getUuid()), query);
            case WEEK -> analytics.getAggregateWeekAnalytic(new Realm(realm.getUuid()), query);
            case MONTH -> analytics.getAggregateMonthAnalytic(new Realm(realm.getUuid()), query);
        };
    }

    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("summary")
    public OrderSummaryAnalytic index(@RequestHeader("X-Market-Identity") RealmIdentity realm) {
        return new OrderSummaryAnalytic(
                analytics.getDayOrdersCount(new Realm(realm.getUuid())),
                analytics.getNewOrderCount(new Realm(realm.getUuid()))
        );
    }
}

package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.projection.OrderDateAnalytic;
import com.creelayer.marketplace.crm.order.core.projection.OrderSummaryAnalytic;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticQuery;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticSummaryQuery;
import com.creelayer.marketplace.crm.order.http.dto.OrderAnalyticSearchFilter;
import com.creelayer.marketplace.crm.order.http.mapper.OrderAnalyticsMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("order/analytics")
public class AnalyticsController {

    private QueryHandler<OrderAnalyticQuery, List<OrderDateAnalytic>> analytics;

    private QueryHandler<OrderAnalyticSummaryQuery, OrderSummaryAnalytic> summary;

    private OrderAnalyticsMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("")
    public List<OrderDateAnalytic> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid OrderAnalyticSearchFilter filter
    ) {
        return analytics.ask(mapper.map(realm, filter));
    }

    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("summary")
    public OrderSummaryAnalytic index(@RequestHeader("X-Market-Identity") RealmIdentity realm) {
        return summary.ask(new OrderAnalyticSummaryQuery(realm.getUuid()));
    }
}

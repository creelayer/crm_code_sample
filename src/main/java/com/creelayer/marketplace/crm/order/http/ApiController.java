package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import com.creelayer.marketplace.crm.order.core.projection.OrderApiSearchResult;
import com.creelayer.marketplace.crm.order.core.projection.OrderViewDetail;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@Validated
@RestController("OrderApi")
@RequestMapping("v1/order")
public class ApiController {

    private QueryHandler<OrderSearchQuery, Page<OrderApiSearchResult>> search;

    private OrderRepository repository;

    @GetMapping("")
    public Page<OrderApiSearchResult> orders(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @RequestHeader("X-Client-Phone") Client client
    ) {
        return search.ask(new OrderSearchQuery(realm.getUuid(), client.getUuid()));
    }

    @GetMapping("{order}")
    public OrderViewDetail view(@PathVariable UUID order) {
        return repository.findByUuid(order, OrderViewDetail.class)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}

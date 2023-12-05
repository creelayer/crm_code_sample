package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.OrderSearch;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.http.dto.OrderApiSearchResultResponse;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import com.creelayer.marketplace.crm.order.http.dto.OrderApiDetailResponse;
import com.creelayer.marketplace.crm.order.http.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Validated
@RestController("OrderApi")
@RequestMapping("v1/order")
public class ApiController {

    private final OrderSearch search;

    private final OrderMapper orderMapper;

    @GetMapping("")
    public Page<OrderApiSearchResultResponse> orders(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @RequestHeader("X-Client-Phone") Client client,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable
    ) {
        return search.search(realm, new OrderSearchQuery(client.getUuid()), pageable, OrderApiSearchResultResponse.class);
    }

    @GetMapping("{order}")
    public OrderApiDetailResponse view(@PathVariable Order order) {
        return orderMapper.map(order);
    }
}

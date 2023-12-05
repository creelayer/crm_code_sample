package com.creelayer.marketplace.crm.order.http;

import com.creelayer.activity.entity.ActivityEntity;
import com.creelayer.activity.storage.ActivityStorage;
import com.creelayer.activity.entity.ActivityIdentity;
import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.OrderManageProcess;
import com.creelayer.marketplace.crm.order.core.OrderRepository;
import com.creelayer.marketplace.crm.order.core.command.RemoveOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.http.dto.*;
import com.creelayer.marketplace.crm.order.http.mapper.OrderMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {

    private OrderRepository orderRepository;

    private OrderManageProcess orderManage;

    private OrderMapper orderMapper;

    private ActivityStorage activityStorage;

    @JsonIgnoreProperties({"items"})
    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("")
    public Page<OrderSearchResultResponse> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid OrderSearchFilter filter,
            @PageableDefault(sort = {"createdAt"}, size = 50, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return orderRepository.search(realm, orderMapper.map(filter), pageable, OrderSearchResultResponse.class);
    }

    @PreAuthorize("hasPermission(#order.realm, 'order_read')")
    @GetMapping("{order}/activity")
    public List<ActivityEntity> activity(@PathVariable Order order) {
        return activityStorage.findAll(new ActivityIdentity(order.getUuid().toString(), order.getClass()));
    }

    @PreAuthorize("hasPermission(#order.realm, 'order_read')")
    @GetMapping("{order}")
    public OrderViewResponse view(@PathVariable Order order) {
        return orderMapper.viewMap(order);
    }

    @PreAuthorize("hasPermission(#order.realm, 'order_read')")
    @GetMapping("{order}/short")
    public OrderShortDetailResponse shortView(@PathVariable Order order) {
        return orderMapper.shortMap(order);
    }

    @ActivityLog(data = "returnObject.status", type = "ORDER_UPDATE", comment = "#request.comment")
    @PreAuthorize("hasPermission(#order.realm, 'order_manage')")
    @PostMapping("{order}/status")
    public OrderViewResponse updateStatus(@PathVariable Order order, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orderMapper.viewMap(orderManage.manage(orderMapper.map(order, request)));
    }

    @ActivityLog(data = "returnObject.payoutStatus", type = "ORDER_UPDATE", comment = "'payout'")
    @PreAuthorize("hasPermission(#order.realm, 'order_manage')")
    @PostMapping("{order}/confirm-payout")
    public OrderViewResponse confirmPayout(@PathVariable Order order) {
        return orderMapper.viewMap(orderManage.confirmPayout(order.getUuid()));
    }

    @ActivityLog(data = "returnObject.items", type = "ORDER_ADD_ITEM")
    @PreAuthorize("hasPermission(#order.realm, 'order_manage')")
    @PostMapping("{order}/item/add")
    public OrderViewResponse addItem(@PathVariable Order order, @RequestBody @Valid AddOrderItemRequest request) {
        return orderMapper.viewMap(orderManage.addItem(orderMapper.map(order, request)));
    }

    @ActivityLog(data = "returnObject.items", type = "ORDER_REMOVE_ITEM")
    @PreAuthorize("hasPermission(#order.realm, 'order_manage')")
    @PostMapping("{order}/item/{sku}/remove")
    public OrderViewResponse removeItem(@PathVariable Order order, @PathVariable String sku) {
        return orderMapper.viewMap(orderManage.removeItem(new RemoveOrderItemCountCommand(order.getUuid(), sku)));
    }

    @ActivityLog(data = "returnObject.items", type = "ORDER_UPDATE_ITEM_COUNT")
    @PreAuthorize("hasPermission(#order.realm, 'order_manage')")
    @PostMapping("{order}/item/count")
    public OrderViewResponse updateItemCount(@PathVariable Order order, @RequestBody UpdateOrderItemCountRequest request) {
        return orderMapper.viewMap(orderManage.updateItemCount(orderMapper.map(order, request)));
    }
}

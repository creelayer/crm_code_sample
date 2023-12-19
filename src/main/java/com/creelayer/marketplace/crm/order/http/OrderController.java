package com.creelayer.marketplace.crm.order.http;

import com.creelayer.activity.entity.ActivityEntity;
import com.creelayer.activity.storage.ActivityStorage;
import com.creelayer.activity.entity.ActivityIdentity;
import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.incoming.OrderManageProcess;
import com.creelayer.marketplace.crm.order.core.command.RemoveOrderItemCommand;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import com.creelayer.marketplace.crm.order.core.projection.OrderSearchResult;
import com.creelayer.marketplace.crm.order.core.projection.OrderShortDetail;
import com.creelayer.marketplace.crm.order.core.projection.OrderViewDetail;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import com.creelayer.marketplace.crm.order.http.dto.*;
import com.creelayer.marketplace.crm.order.http.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {

    private OrderManageProcess orderManage;

    private OrderMapper orderMapper;

    private ActivityStorage activityStorage;

    private QueryHandler<OrderSearchQuery, Page<OrderSearchResult>> search;

    private OrderRepository orderRepository;

    @PreAuthorize("hasPermission(#realm, 'order_read')")
    @GetMapping("")
    public Page<OrderSearchResult> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid OrderSearchFilter filter
    ) {
        return search.ask(orderMapper.map(realm, filter));
    }

    @PreAuthorize("hasPermission(#order, 'Order', 'order_read')")
    @GetMapping("{order}/activity")
    public List<ActivityEntity> activity(@PathVariable UUID order) {
        return activityStorage.findAll(new ActivityIdentity(order.toString(), order.getClass()));
    }

    @PreAuthorize("hasPermission(#order, 'Order', 'order_read')")
    @GetMapping("{order}")
    public OrderViewDetail view(@PathVariable UUID order) {
        return orderRepository.findByUuid(order, OrderViewDetail.class)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @PreAuthorize("hasPermission(#order, 'Order', 'order_read')")
    @GetMapping("{order}/short")
    public OrderShortDetail shortView(@PathVariable UUID order) {
        return orderRepository.findByUuid(order, OrderShortDetail.class)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @ActivityLog(data = "#request", entity = "#order", type = "ORDER_UPDATE", comment = "#request.comment")
    @PreAuthorize("hasPermission(#order, 'Order', 'order_manage')")
    @PostMapping("{order}/status")
    public void updateStatus(@PathVariable UUID order, @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderManage.manage(orderMapper.map(order, request));
    }

    @ActivityLog(data = "''", entity = "#order", type = "ORDER_CONFIRM_PAYOUT")
    @PreAuthorize("hasPermission(#order, 'Order', 'order_manage')")
    @PostMapping("{order}/confirm-payout")
    public void confirmPayout(@PathVariable UUID order) {
        orderManage.confirmPayout(order);
    }

    @ActivityLog(data = "#request", entity = "#order", type = "ORDER_ADD_ITEM")
    @PreAuthorize("hasPermission(#order, 'Order', 'order_manage')")
    @PostMapping("{order}/item/add")
    public void addItem(@PathVariable UUID order, @RequestBody @Valid AddOrderItemRequest request) {
        orderManage.addItem(orderMapper.map(order, request));
    }

    @ActivityLog(data = "#sku", entity = "#order", type = "ORDER_REMOVE_ITEM")
    @PreAuthorize("hasPermission(#order, 'Order', 'order_manage')")
    @PostMapping("{order}/item/{sku}/remove")
    public void removeItem(@PathVariable UUID order, @PathVariable String sku) {
        orderManage.removeItem(new RemoveOrderItemCommand(order, sku));
    }

    @ActivityLog(data = "#request", entity = "#order", type = "ORDER_UPDATE_ITEM_COUNT")
    @PreAuthorize("hasPermission(#order, 'Order', 'order_manage')")
    @PostMapping("{order}/item/count")
    public void updateItemCount(@PathVariable UUID order, @RequestBody UpdateOrderItemCountRequest request) {
        orderManage.updateItemCount(orderMapper.map(order, request));
    }
}

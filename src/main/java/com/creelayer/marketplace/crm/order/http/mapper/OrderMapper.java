package com.creelayer.marketplace.crm.order.http.mapper;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.command.AddOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderStatusCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import com.creelayer.marketplace.crm.order.http.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "uuid", source = "order.uuid")
    @Mapping(target = "promoCode", source = "request.promoCode")
    AddOrderItemCommand map(Order order, AddOrderItemRequest request);

    @Mapping(target = "uuid", source = "order.uuid")
    UpdateOrderItemCountCommand map(Order order, UpdateOrderItemCountRequest request);

    @Mapping(target = "uuid", source = "order.uuid")
    @Mapping(target = "status", source = "request.status")
    UpdateOrderStatusCommand map(Order order, UpdateOrderStatusRequest request);

    OrderSearchQuery map(OrderSearchFilter filter);

    OrderApiDetailResponse map(Order order);

    OrderViewResponse viewMap(Order order);

    OrderShortDetailResponse shortMap(Order order);

    default OrderApiDetailResponse.Status map(String value){
        OrderApiDetailResponse.Status status =  new OrderApiDetailResponse.Status();
        status.name = value;
        return status;
    }
}

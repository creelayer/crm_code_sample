package com.creelayer.marketplace.crm.order.http.mapper;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.command.AddOrderItemCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderItemCountCommand;
import com.creelayer.marketplace.crm.order.core.command.UpdateOrderStatusCommand;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import com.creelayer.marketplace.crm.order.http.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    AddOrderItemCommand map(UUID order, AddOrderItemRequest request);

    UpdateOrderItemCountCommand map(UUID order, UpdateOrderItemCountRequest request);

    UpdateOrderStatusCommand map(UUID order, UpdateOrderStatusRequest request);

    @Mapping(target = "realm", source = "realm.uuid")
    OrderSearchQuery map(RealmIdentity realm, OrderSearchFilter filter);
}

package com.creelayer.marketplace.crm.order.http.mapper;

import com.creelayer.marketplace.crm.order.http.dto.OrderReviewResponse;
import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.http.dto.CheckoutRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckoutMapper {

    @Mapping(target = "client", source = "phone")
    CheckoutCommand map(String phone, CheckoutRequest request);

    @Mapping(target = "payment", source = "payment.type")
    OrderReviewResponse map(Order order);

    default CheckoutCommand.Client map(String phone){

        if(phone == null)
            return null;

        return new CheckoutCommand.Client(phone);
    }
}

package com.creelayer.marketplace.crm.order.http.mapper;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.command.CheckoutCommand;
import com.creelayer.marketplace.crm.order.http.dto.CheckoutRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckoutMapper {

    @Mapping(target = "realm", source = "realm.uuid")
    @Mapping(target = "client", source = "phone")
    CheckoutCommand map(RealmIdentity realm, String phone, CheckoutRequest request);

    default CheckoutCommand.Client map(String phone){

        if(phone == null || phone.isEmpty())
            return null;

        return new CheckoutCommand.Client(phone);
    }
}

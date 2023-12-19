package com.creelayer.marketplace.crm.order.http.mapper;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.query.OrderAnalyticQuery;
import com.creelayer.marketplace.crm.order.http.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderAnalyticsMapper {

    @Mapping(target = "realm", source = "realm.uuid")
    OrderAnalyticQuery map(RealmIdentity realm, OrderAnalyticSearchFilter request);
}

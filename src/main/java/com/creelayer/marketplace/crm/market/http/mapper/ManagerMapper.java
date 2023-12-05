package com.creelayer.marketplace.crm.market.http.mapper;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.ManagerSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManagerMapper {

    @Mapping(target = "market", source = "realm.uuid")
    ManagerSearchQuery map(RealmIdentity realm, ManagerSearchFilter filter);

    @Mapping(target = "market", source = "uuid")
    UpdateMarketCommand map(UUID uuid, UpdateMarketRequest request);

//    @Mapping(target = "account", source = "account")
//    @Mapping(target = "name", source = "dto.name")
//    @Mapping(target = "email", source = "dto.email")
//    @Mapping(target = "secret", expression = "java(RandomString.generate())")
//    Market map(Account account, CreateMarketRequest request);
//
//    Market map(CreateMarketRequest dto, @MappingTarget Market market);
}

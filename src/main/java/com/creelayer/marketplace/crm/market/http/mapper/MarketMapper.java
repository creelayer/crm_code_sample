package com.creelayer.marketplace.crm.market.http.mapper;

import com.creelayer.marketplace.crm.market.core.command.CreateMarketCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.CreateMarketRequest;
import com.creelayer.marketplace.crm.market.http.dto.MarketSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MarketMapper {

    @Mapping(target = "account", source = "uuid")
    MarketSearchQuery map(UUID uuid, MarketSearchFilter filter);

    @Mapping(target = "account", source = "uuid")
    CreateMarketCommand map(UUID uuid, CreateMarketRequest request);

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

package com.creelayer.marketplace.crm.market.http.mapper;

import com.creelayer.marketplace.crm.market.core.command.CreateMarketCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.CreateMarketRequest;
import com.creelayer.marketplace.crm.market.http.dto.MarketSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MarketMapper {

    MarketSearchQuery map(UUID account, MarketSearchFilter filter);

    CreateMarketCommand map(UUID account, CreateMarketRequest request);

    UpdateMarketCommand map(UUID market, UpdateMarketRequest request);
}

package com.creelayer.marketplace.crm.market.http.mapper;

import com.creelayer.marketplace.crm.market.core.command.UpdateMarketCommand;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.ManagerSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManagerMapper {

    ManagerSearchQuery map(UUID market, ManagerSearchFilter filter);

    UpdateMarketCommand map(UUID market, UpdateMarketRequest request);
}

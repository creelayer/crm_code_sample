package com.creelayer.marketplace.crm.client.http.mapper;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientApiCommand;
import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import com.creelayer.marketplace.crm.client.http.dto.ClientSearchFilter;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientSearchQuery map(UUID realm, ClientSearchFilter filter);

    UpdateClientCommand map(UUID client, UpdateClientRequest request);

    UpdateClientApiCommand map(UUID realm, String phone, UpdateClientRequest request);
}

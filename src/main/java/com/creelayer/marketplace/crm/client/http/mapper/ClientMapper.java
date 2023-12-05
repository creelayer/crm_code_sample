package com.creelayer.marketplace.crm.client.http.mapper;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import com.creelayer.marketplace.crm.client.http.dto.ClientSearchFilter;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientSearchQuery map(ClientSearchFilter filter);

    @Mapping(target = "uuid", source = "client.uuid")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "name", source = "request.name")
    UpdateClientCommand map(Client client, UpdateClientRequest request);
}

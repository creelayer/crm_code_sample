package com.creelayer.marketplace.crm.promo.http.mapper;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoGroupSearchFilter;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoGroupRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoGroupMapper {

    PromoGroupSearchQuery map(PromoGroupSearchFilter filter);

    CreatePromoGroupCommand map(CreatePromoGroupRequest request);

    @Mapping(target = "uuid", source = "group.uuid")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "name", source = "request.name")
    UpdatePromoGroupCommand map(PromoGroup group, UpdatePromoGroupRequest request);

}

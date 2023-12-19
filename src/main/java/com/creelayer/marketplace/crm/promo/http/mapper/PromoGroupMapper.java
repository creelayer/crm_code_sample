package com.creelayer.marketplace.crm.promo.http.mapper;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoGroupCommand;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoGroupSearchFilter;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoGroupRequest;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoGroupMapper {

    PromoGroupSearchQuery map(UUID realm, PromoGroupSearchFilter filter);

    CreatePromoGroupCommand map(UUID realm, CreatePromoGroupRequest request);

    UpdatePromoGroupCommand map(UUID group, UpdatePromoGroupRequest request);

}

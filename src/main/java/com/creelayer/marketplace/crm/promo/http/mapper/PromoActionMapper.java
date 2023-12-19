package com.creelayer.marketplace.crm.promo.http.mapper;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoActionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoConditionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoActionSearchFilter;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoActionRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoActionMapper {

    PromoActionSearchQuery map(UUID realm, PromoActionSearchFilter filter);

    CreatePromoActionCommand map(UUID group, CreatePromoActionRequest request);

    UpdatePromoActionCommand map(UUID action, UpdatePromoActionRequest request);

    @Mapping(target = "entity", source = "action")
    ManagePromoConditionCommand map(UUID action, List<PromoConditionRequest> conditions);

}

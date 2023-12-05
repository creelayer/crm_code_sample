package com.creelayer.marketplace.crm.promo.http.mapper;

import com.creelayer.marketplace.crm.promo.core.command.CreatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoActionCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoActionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.ManagePromoConditionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoActionSearchFilter;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoActionMapper {

    PromoActionSearchQuery map(PromoActionSearchFilter filter);

    @Mapping(target = "group", source = "group.uuid")
    @Mapping(target = "name", source = "request.name")
    CreatePromoActionCommand map(PromoGroup group, CreatePromoActionRequest request);

    @Mapping(target = "action", source = "action.uuid")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "locales", source = "request.locales")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "timer", source = "request.timer")
    @Mapping(target = "features", source = "request.features")
    @Mapping(target = "expiredAt", source = "request.expiredAt")
    UpdatePromoActionCommand map(PromoAction action, CreatePromoActionRequest request);

    @Mapping(target = "uuid", source = "action.uuid")
    @Mapping(target = "conditions", source = "conditions")
    ManagePromoConditionCommand map(PromoAction action, List<ManagePromoConditionRequest> conditions);

}

package com.creelayer.marketplace.crm.promo.http.mapper;


import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.*;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoCodeMapper {

    PromoCodeSearchQuery map(PromoCodeSearchFilter filter);

    @Mapping(target = "group", source = "group.uuid")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "status", source = "request.status")
    GeneratePromoCodeCommand map(PromoGroup group, CreatePromoCodeRequest request);

    @Mapping(target = "code", source = "code.uuid")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "expiredAt", source = "request.expiredAt")
    @Mapping(target = "status", source = "request.status")
    UpdatePromoCodeCommand map(PromoCode code, UpdatePromoCodeRequest request);

    @Mapping(target = "uuid", source = "code.uuid")
    @Mapping(target = "conditions", source = "conditions")
    ManagePromoConditionCommand map(PromoCode code, List<ManagePromoConditionRequest> conditions);

    PromoCodeCheckDetailResponse map(PromoCode code);
}

package com.creelayer.marketplace.crm.promo.http.mapper;

import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.*;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoCodeMapper {

    PromoCodeSearchQuery map(UUID realm, PromoCodeSearchFilter filter);

    GeneratePromoCodeCommand map(UUID group, GeneratePromoCodeRequest request);

    UpdatePromoCodeCommand map(UUID code, UpdatePromoCodeRequest request);

    ManagePromoConditionCommand map(UUID entity, List<PromoConditionRequest> conditions);
}

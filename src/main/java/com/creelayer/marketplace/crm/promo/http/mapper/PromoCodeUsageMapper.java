package com.creelayer.marketplace.crm.promo.http.mapper;


import com.creelayer.marketplace.crm.promo.core.query.PromoCodeUsageSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeUsageSearchFilter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoCodeUsageMapper {
    PromoCodeUsageSearchQuery map(PromoCodeUsageSearchFilter filter);
}

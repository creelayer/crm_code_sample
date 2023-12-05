package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsageSearch;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeUsageSearchResult;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeUsageSearchFilter;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoCodeUsageMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("promo/code/usage")
public class PromoCodeUsageController {


    private PromoCodeUsageSearch promoCodeUsageSearch;

    private PromoCodeUsageMapper codeUsageMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoCodeUsageSearchResult> search(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoCodeUsageSearchFilter filter, Pageable pageable) {
        return promoCodeUsageSearch.search(new Realm(realm.getUuid()), codeUsageMapper.map(filter), pageable);
    }

}

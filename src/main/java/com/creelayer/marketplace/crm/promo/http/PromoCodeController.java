package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeManage;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchResult;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeViewDetail;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.GeneratePromoCodeRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoConditionRequest;
import com.creelayer.marketplace.crm.promo.core.PromoCodeService;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeSearchFilter;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoCodeRequest;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoCodeMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("promo/code")
public class PromoCodeController {

    private PromoCodeService promoCodeService;

    private QueryHandler<PromoCodeSearchQuery, Page<PromoCodeSearchResult>> search;

    private PromoCodeRepository codeRepository;

    private PromoCodeManage promoCodeManage;

    private PromoCodeMapper promoCodeMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoCodeSearchResult> search(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoCodeSearchFilter filter) {
        return search.ask(promoCodeMapper.map(realm.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#code, 'PromoCode', 'promo_code_read') || hasPermission(#code, 'PromoCode', 'promo_code_manage')")
    @GetMapping("{code}")
    public PromoCodeViewDetail view(@PathVariable UUID code) {
        return codeRepository.findByUuid(code, PromoCodeViewDetail.class)
                .orElseThrow(() -> new NotFoundException("Promo code not found"));
    }

    @ActivityLog(entity = "#group", data = "#request", type = "PROMO_GROUP_CODE_GENERATE")
    @PreAuthorize(" hasPermission(#group, 'PromoGroup', 'promo_code_manage')")
    @PostMapping("generate/{group}")
    public void generate(@PathVariable UUID group, @Valid @RequestBody GeneratePromoCodeRequest request) {
        promoCodeService.generate(promoCodeMapper.map(group, request));
    }

    @ActivityLog(entity = "#code", data = "#request", type = "PROMO_CODE_UPDATE")
    @PreAuthorize("hasPermission(#code, 'PromoCode', 'promo_code_manage')")
    @PutMapping("{code}")
    public void update(@PathVariable UUID code, @Valid @RequestBody UpdatePromoCodeRequest request) {
        promoCodeManage.update(promoCodeMapper.map(code, request));
    }

    @ActivityLog(entity = "#code", data = "#conditions", type = "PROMO_CODE_CONDITION_UPDATE")
    @PreAuthorize("hasPermission(#code, 'PromoCode', 'promo_code_manage')")
    @PostMapping("{code}/condition")
    public void updateCondition(@PathVariable UUID code, @Valid @RequestBody List<PromoConditionRequest> conditions) {
        promoCodeManage.update(promoCodeMapper.map(code, conditions));
    }

    @ActivityLog(entity = "#code", data = "#code", type = "PROMO_CODE_DELETE")
    @PreAuthorize("hasPermission(#code, 'PromoCode', 'promo_code_manage')")
    @DeleteMapping("{code}")
    public void delete(@PathVariable UUID code) {
        promoCodeManage.remove(code);
    }
}

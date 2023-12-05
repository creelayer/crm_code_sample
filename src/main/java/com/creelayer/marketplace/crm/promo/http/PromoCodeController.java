package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeManage;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeSearch;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCondition;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchResult;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoCodeRequest;
import com.creelayer.marketplace.crm.promo.http.dto.ManagePromoConditionRequest;
import com.creelayer.marketplace.crm.promo.core.PromoCodeService;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeSearchFilter;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoCodeRequest;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoCodeMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("promo/code")
public class PromoCodeController {

    private PromoCodeService promoCodeService;

    private PromoCodeSearch promoCodeSearch;

    private PromoCodeManage promoCodeManage;

    private PromoCodeMapper promoCodeMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoCodeSearchResult> search(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoCodeSearchFilter filter, Pageable pageable) {
        return promoCodeSearch.search(new Realm(realm.getUuid()), promoCodeMapper.map(filter), pageable, PromoCodeSearchResult.class);
    }

    @PreAuthorize("hasPermission(#code.group.realm, 'promo_code_read') || hasPermission(#code.group.realm, 'promo_code_manage')")
    @GetMapping("{code}")
    public PromoCode view(@PathVariable PromoCode code) {
        return code;
    }

    @ActivityLog(entity = "#group", data = "#dto", type = "PROMO_GROUP_CODE_GENERATE")
    @PreAuthorize(" hasPermission(#group.realm, 'promo_code_manage')")
    @PostMapping("generate/{group}")
    public void generate(@PathVariable PromoGroup group, @Valid @RequestBody CreatePromoCodeRequest request) {
        promoCodeService.generate(promoCodeMapper.map(group, request));
    }

    @ActivityLog(data = "#request", type = "PROMO_CODE_UPDATE")
    @PreAuthorize("hasPermission(#code.group.realm, 'promo_code_manage')")
    @PutMapping("{code}")
    public PromoCode update(@PathVariable PromoCode code, @Valid @RequestBody UpdatePromoCodeRequest request) {
        return promoCodeManage.update(promoCodeMapper.map(code, request));
    }

    @PreAuthorize("hasPermission(#code.group.realm, 'promo_code_manage')")
    @GetMapping("{code}/condition")
    public List<PromoCondition> getCondition(@PathVariable PromoCode code) {
        return code.getConditions();
    }

    @ActivityLog(entity = "#code", data = "#conditions", type = "PROMO_CODE_CONDITION_UPDATE")
    @PreAuthorize("hasPermission(#code.group.realm, 'promo_code_manage')")
    @PostMapping("{code}/condition")
    public void updateCondition(@PathVariable PromoCode code, @Valid @RequestBody List<ManagePromoConditionRequest> conditions) {
        promoCodeManage.update(promoCodeMapper.map(code, conditions));
    }

    @ActivityLog(entity = "#code", data = "#code", type = "PROMO_CODE_DELETE")
    @PreAuthorize("hasPermission(#code.group.realm, 'promo_code_manage')")
    @DeleteMapping("{code}")
    public void delete(@PathVariable PromoCode code) {
        promoCodeManage.remove(code.getUuid());
    }
}

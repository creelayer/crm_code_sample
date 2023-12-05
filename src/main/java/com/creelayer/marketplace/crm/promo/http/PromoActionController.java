package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCondition;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionSearchResult;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoActionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.ManagePromoConditionRequest;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoActionManage;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoActionSearch;
import com.creelayer.marketplace.crm.promo.core.model.PromoAction;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoActionMapper;
import com.creelayer.marketplace.crm.promo.http.dto.PromoActionSearchFilter;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("promo/action")
public class PromoActionController {

    private PromoActionSearch promoActionSearch;

    private PromoActionManage promoActionManage;

    private PromoActionMapper promoActionMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoActionSearchResult> search(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoActionSearchFilter filter, Pageable pageable) {
        return promoActionSearch.search(new Realm(realm.getUuid()), promoActionMapper.map(filter), pageable);
    }

    @PreAuthorize("hasPermission(#action.group.realm, 'promo_code_read') || hasPermission(#action.group.realm, 'promo_code_manage')")
    @GetMapping("{action}")
    public PromoAction view(@PathVariable PromoAction action) {
        return action;
    }

    @ActivityLog(data = "#request", type = "PROMO_ACTION_UPDATE")
    @PreAuthorize("hasPermission(#group.realm, 'promo_code_manage')")
    @PostMapping("{group}")
    public PromoAction create(@PathVariable PromoGroup group, @Valid @RequestBody CreatePromoActionRequest request) {
        return promoActionManage.create(promoActionMapper.map(group, request));
    }

    @ActivityLog(data = "#request", type = "PROMO_ACTION_UPDATE")
    @PreAuthorize("hasPermission(#action.group.realm, 'promo_code_manage')")
    @PutMapping("{action}")
    public PromoAction update(@PathVariable PromoAction action, @Valid @RequestBody CreatePromoActionRequest request) {
        return promoActionManage.update(promoActionMapper.map(action, request));
    }

    @PreAuthorize("hasPermission(#action.group.realm, 'promo_code_manage')")
    @GetMapping("{action}/condition")
    public List<PromoCondition> getCondition(@PathVariable PromoAction action) {
        return action.getConditions();
    }

    @ActivityLog(entity = "#action", data = "#conditions", type = "PROMO_ACTION_CONDITION_UPDATE")
    @PreAuthorize("hasPermission(#action.group.realm, 'promo_code_manage')")
    @PostMapping("{action}/condition")
    public void updateCondition(@PathVariable PromoAction action, @Valid @RequestBody List<ManagePromoConditionRequest> conditions) {
        promoActionManage.update(promoActionMapper.map(action, conditions));
    }

    @ActivityLog(entity = "#action", data = "#action", type = "PROMO_ACTION_DELETE")
    @PreAuthorize("hasPermission(#action.group.realm, 'promo_code_manage')")
    @DeleteMapping("{action}")
    public void delete(@PathVariable PromoAction action) {
        promoActionManage.remove(action.getUuid());
    }
}

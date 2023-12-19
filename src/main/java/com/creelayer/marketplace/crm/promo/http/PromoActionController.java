package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoActionRepository;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionSearchResult;
import com.creelayer.marketplace.crm.promo.core.projection.PromoActionViewDetail;
import com.creelayer.marketplace.crm.promo.core.query.PromoActionSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoActionRequest;
import com.creelayer.marketplace.crm.promo.http.dto.PromoConditionRequest;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoActionManage;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoActionRequest;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoActionMapper;
import com.creelayer.marketplace.crm.promo.http.dto.PromoActionSearchFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("promo/action")
public class PromoActionController {

    private QueryHandler<PromoActionSearchQuery, Page<PromoActionSearchResult>> search;

    private PromoActionRepository actionRepository;

    private PromoActionManage promoActionManage;

    private PromoActionMapper promoActionMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoActionSearchResult> search(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoActionSearchFilter filter) {
        return search.ask(promoActionMapper.map(realm.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#action, 'PromoAction', 'promo_code_read') || hasPermission(#action, 'PromoAction', 'promo_code_manage')")
    @GetMapping("{action}")
    public PromoActionViewDetail view(@PathVariable UUID action) {
        return actionRepository.findByUuid(action, PromoActionViewDetail.class)
                .orElseThrow(() -> new NotFoundException("Action not found"));
    }

    @ActivityLog(data = "#request", type = "PROMO_ACTION_CREATE")
    @PreAuthorize("hasPermission(#group, 'PromoGroup', 'promo_code_manage')")
    @PostMapping("{group}")
    public UUID create(@PathVariable UUID group, @Valid @RequestBody CreatePromoActionRequest request) {
        return promoActionManage.create(promoActionMapper.map(group, request));
    }

    @ActivityLog(entity = "#action", data = "#request", type = "PROMO_ACTION_UPDATE")
    @PreAuthorize("hasPermission(#action, 'PromoAction', 'promo_code_manage')")
    @PutMapping("{action}")
    public void update(@PathVariable UUID action, @Valid @RequestBody UpdatePromoActionRequest request) {
        promoActionManage.update(promoActionMapper.map(action, request));
    }

    @ActivityLog(entity = "#action", data = "#conditions", type = "PROMO_ACTION_CONDITION_UPDATE")
    @PreAuthorize("hasPermission(#action, 'PromoAction', 'promo_code_manage')")
    @PostMapping("{action}/condition")
    public void updateCondition(@PathVariable UUID action, @Valid @RequestBody List<PromoConditionRequest> conditions) {
        promoActionManage.update(promoActionMapper.map(action, conditions));
    }

    @ActivityLog(entity = "#action", data = "#action", type = "PROMO_ACTION_DELETE")
    @PreAuthorize("hasPermission(#action, 'PromoAction', 'promo_code_manage')")
    @DeleteMapping("{action}")
    public void delete(@PathVariable UUID action) {
        promoActionManage.remove(action);
    }
}

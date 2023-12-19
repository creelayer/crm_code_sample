package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupManage;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoGroupRepository;
import com.creelayer.marketplace.crm.promo.core.projection.PromoDetailView;
import com.creelayer.marketplace.crm.promo.core.projection.PromoGroupSearchResult;
import com.creelayer.marketplace.crm.promo.core.query.PromoGroupSearchQuery;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoGroupMapper;
import com.creelayer.marketplace.crm.promo.http.dto.PromoGroupSearchFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("promo/group")
public class PromoGroupController {

    private PromoGroupManage manage;

    private PromoGroupMapper mapper;

    private QueryHandler<PromoGroupSearchQuery, Page<PromoGroupSearchResult>> search;

    private PromoGroupRepository groupRepository;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoGroupSearchResult> groups(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid PromoGroupSearchFilter filter
    ) {
        return search.ask(mapper.map(realm.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#group, 'PromoGroup', 'promo_code_manage')")
    @GetMapping("{group}")
    public PromoDetailView view(@PathVariable UUID group) {
        return groupRepository.findByUuid(group, PromoDetailView.class)
                .orElseThrow(() -> new NotFoundException("Group not found"));
    }

    @ActivityLog(data = "#request", type = "PROMO_GROUP_CREATE")
    @PreAuthorize("hasPermission(#realm, 'promo_code_manage')")
    @PostMapping("")
    public UUID create(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid @RequestBody CreatePromoGroupRequest request) {
        return manage.create(mapper.map(realm.getUuid(), request));
    }

    @ActivityLog(data = "#request", entity = "#group", type = "PROMO_GROUP_UPDATE")
    @PreAuthorize("hasPermission(#group, 'PromoGroup', 'promo_code_manage')")
    @PutMapping("{group}")
    public void update(@PathVariable UUID group, @Valid @RequestBody UpdatePromoGroupRequest request) {
        manage.update(mapper.map(group, request));
    }

    @ActivityLog(data = "#group", entity = "#group", type = "PROMO_GROUP_DELETE")
    @PreAuthorize("hasPermission(#group, 'PromoGroup', 'promo_code_manage')")
    @DeleteMapping("{group}")
    public void delete(@PathVariable UUID group) {
        manage.remove(group);
    }

}

package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupManage;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoGroupSearch;
import com.creelayer.marketplace.crm.promo.core.projection.PromoGroupSearchResult;
import com.creelayer.marketplace.crm.promo.http.dto.CreatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.dto.UpdatePromoGroupRequest;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoGroupMapper;
import com.creelayer.marketplace.crm.promo.core.model.PromoGroup;
import com.creelayer.marketplace.crm.promo.http.dto.PromoGroupSearchFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("promo/group")
public class PromoGroupController {

    private PromoGroupManage promoGroupManage;

    private PromoGroupSearch promoGroupSearch;

    private PromoGroupMapper promoGroupMapper;

    @PreAuthorize("hasPermission(#realm, 'promo_code_read') || hasPermission(#realm, 'promo_code_manage')")
    @GetMapping("search")
    public Page<PromoGroupSearchResult> groups(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid PromoGroupSearchFilter filter, @PageableDefault(sort = "createdAt") Pageable pageable) {
        return promoGroupSearch.search(new Realm(realm.getUuid()), promoGroupMapper.map(filter), pageable);
    }

    @PreAuthorize("hasPermission(#group.realm, 'promo_code_read') || hasPermission(#group.realm, 'promo_code_manage')")
    @GetMapping("{group}")
    public PromoGroup viewGroup(@PathVariable PromoGroup group) {
        return group;
    }

    @ActivityLog(data = "#request", type = "PROMO_GROUP_CREATE")
    @PreAuthorize("hasPermission(#realm, 'promo_code_manage')")
    @PostMapping("")
    public PromoGroup createGroup(@RequestHeader("X-Market-Identity") RealmIdentity realm, @Valid @RequestBody CreatePromoGroupRequest request) {
        return promoGroupManage.create(new Realm(realm.getUuid()), promoGroupMapper.map(request));
    }

    @ActivityLog(data = "#request", type = "PROMO_GROUP_UPDATE")
    @PreAuthorize("hasPermission(#group.realm, 'promo_code_manage')")
    @PutMapping("{group}")
    public PromoGroup updateGroup(@PathVariable PromoGroup group, @Valid @RequestBody UpdatePromoGroupRequest request) {
        return promoGroupManage.update(promoGroupMapper.map(group, request));
    }

    @ActivityLog(entity = "#group", data = "#group", type = "PROMO_GROUP_DELETE")
    @PreAuthorize("hasPermission(#group.realm, 'promo_code_manage')")
    @DeleteMapping("{group}")
    public void deleteGroup(@PathVariable PromoGroup group) {
        promoGroupManage.remove(group.getUuid());
    }

}

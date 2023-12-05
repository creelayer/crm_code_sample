package com.creelayer.marketplace.crm.market.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.command.CreateManagerCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdatePermissionCommand;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerDetailDistributor;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerManage;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerSearch;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.projection.ManagerView;
import com.creelayer.marketplace.crm.market.core.projection.ManagerSearchResult;
import com.creelayer.marketplace.crm.market.http.dto.ManagerSearchFilter;
import com.creelayer.marketplace.crm.market.http.mapper.ManagerMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("manager")
public class ManagerController {

    private ManagerSearch managerSearch;

    private ManagerDetailDistributor managerDetail;

    private ManagerManage managerManage;

    private ManagerMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'manage_manage')")
    @GetMapping("")
    public Page<ManagerSearchResult> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            ManagerSearchFilter filter,
            @PageableDefault Pageable pageable
    ) {
        return managerSearch.search(mapper.map(realm, filter), pageable, ManagerSearchResult.class);
    }

    @PreAuthorize("hasPermission(#realm, 'manage_manager')")
    @PostMapping("/add/{account}")
    public Manager addManager(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable UUID account) {
        return managerManage.addManager(new CreateManagerCommand(account, realm.getUuid()));
    }

    @PreAuthorize("hasPermission(#manager.market, 'manage_manager')")
    @GetMapping("/{manager}")
    public ManagerView manager(@PathVariable Manager manager) {
        return managerDetail.getManager(manager.getUuid());
    }

    @PreAuthorize("hasPermission(#manager.market, 'manage_manager')")
    @PutMapping("/{manager}/permission")
    public List<String> permission(@PathVariable Manager manager, @RequestBody List<String> scopes) {
        managerManage.updatePermissions(new UpdatePermissionCommand(manager.getUuid(), scopes));
        return scopes;
    }

    @PreAuthorize("hasPermission(#manager.market, 'manage_manager')")
    @DeleteMapping("{manager}")
    public void delete(@PathVariable Manager manager) {
        managerManage.remove(manager.getUuid());
    }
}

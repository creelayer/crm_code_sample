package com.creelayer.marketplace.crm.market.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.command.CreateManagerCommand;
import com.creelayer.marketplace.crm.market.core.command.UpdatePermissionCommand;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerDetailProvider;
import com.creelayer.marketplace.crm.market.core.incoming.ManagerManage;
import com.creelayer.marketplace.crm.market.core.projection.ManagerView;
import com.creelayer.marketplace.crm.market.core.projection.ManagerSearchResult;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.ManagerSearchFilter;
import com.creelayer.marketplace.crm.market.http.mapper.ManagerMapper;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("manager")
public class ManagerController {

    private QueryHandler<ManagerSearchQuery, Page<ManagerSearchResult>> managerSearch;

    private ManagerManage managerManage;

    private ManagerDetailProvider managerDetail;

    private ManagerMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'manage_manage')")
    @GetMapping("")
    public Page<ManagerSearchResult> index(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            ManagerSearchFilter filter
    ) {
        return managerSearch.ask(mapper.map(realm.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#realm, 'manage_manager')")
    @PostMapping("/add/{account}")
    public UUID addManager(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable UUID account) {
       return managerManage.addManager(new CreateManagerCommand(realm.getUuid(), account));
    }

    @PreAuthorize("hasPermission(#manager, 'Manager', 'manage_manager')")
    @GetMapping("/{manager}")
    public ManagerView manager(@PathVariable UUID manager) {
        return managerDetail.detail(manager);
    }

    @PreAuthorize("hasPermission(#manager, 'Manager', 'manage_manager')")
    @PutMapping("/{manager}/permission")
    public void permission(@PathVariable UUID manager, @RequestBody List<String> scopes) {
        managerManage.updatePermissions(new UpdatePermissionCommand(manager, scopes));
    }

    @PreAuthorize("hasPermission(#manager, 'Manager', 'manage_manager')")
    @DeleteMapping("{manager}")
    public void delete(@PathVariable UUID manager) {
        managerManage.remove(manager);
    }
}

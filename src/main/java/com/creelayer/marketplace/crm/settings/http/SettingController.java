package com.creelayer.marketplace.crm.settings.http;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.settings.core.*;
import com.creelayer.marketplace.crm.settings.http.dto.UpdateSettingRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/setting")
public class SettingController {

    private SettingManage settingManage;

    private SettingSearch settingSearch;

    @PreAuthorize("hasPermission(#realm, 'setting_manage')")
    @GetMapping("")
    public List<SettingView> search(@RequestHeader("X-Market-Identity") RealmIdentity realm) {
        return settingSearch.search(new SearchSettingQuery(realm.getUuid()));
    }

    @PreAuthorize("hasPermission(#realm, 'setting_manage')")
    @GetMapping("{name}")
    public SettingView view(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String name) {
        return settingSearch.find(realm.getUuid(), name).orElseThrow(() -> new NotFoundException("Setting no found"));
    }

    @PreAuthorize("hasPermission(#realm, 'setting_manage')")
    @PostMapping("")
    public void upsert(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @Valid @RequestBody UpdateSettingRequest request
    ) {
        settingManage.upsert(new UpsertSettingCommand(
                realm.getUuid(),
                request.name,
                request.value,
                request.description
        ));
    }

    @PreAuthorize("hasPermission(#realm, 'setting_manage')")
    @DeleteMapping("{name}")
    public void delete(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String name) {
        settingManage.delete(realm.getUuid(), name);
    }
}

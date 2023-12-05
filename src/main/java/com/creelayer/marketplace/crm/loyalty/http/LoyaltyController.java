package com.creelayer.marketplace.crm.loyalty.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.loyalty.core.command.RegistrationPromoSettingCommand;
import com.creelayer.marketplace.crm.loyalty.core.model.LoyaltySetting;
import com.creelayer.marketplace.crm.loyalty.core.model.Realm;
import com.creelayer.marketplace.crm.loyalty.core.outgoing.LoyaltySettingRepository;
import com.creelayer.marketplace.crm.loyalty.core.LoyaltySettingService;
import com.creelayer.marketplace.crm.loyalty.http.dto.UpdateRegistrationPromoSettingRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("loyalty/setting")
public class LoyaltyController {

    private LoyaltySettingService loyaltySettingService;

    private LoyaltySettingRepository settingRepository;

    @PreAuthorize("hasPermission(#realm, 'loyalty_manage')")
    @GetMapping("")
    public List<LoyaltySetting> index(@RequestHeader("X-Market-Identity") RealmIdentity realm) {
        return settingRepository.findAllByRealm(new Realm(realm.getUuid()));
    }

    @PreAuthorize("hasPermission(#realm, 'loyalty_manage')")
    @PostMapping("/registration")
    public void save(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @RequestBody UpdateRegistrationPromoSettingRequest request
    ) {
        loyaltySettingService.setting(
                new RegistrationPromoSettingCommand(realm.getUuid(), request.discount, request.validity)
        );
    }
}

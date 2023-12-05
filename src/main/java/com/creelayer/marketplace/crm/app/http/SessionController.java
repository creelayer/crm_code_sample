package com.creelayer.marketplace.crm.app.http;

import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.market.core.incoming.MarketSearch;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class SessionController {

    private MarketSearch marketSearch;

    @GetMapping("session")
    @PreAuthorize("hasPermission(#realm, 'session')")
    public Session session(
            @AuthenticationPrincipal Account account,
            @RequestHeader("X-Market-Identity") RealmIdentity realm
    ) {
        return new Session(account)
                .setMarket(marketSearch.getDetail(realm.getUuid())
                        .orElseThrow(() -> new NotFoundException("Market not found")))
                .managed(marketSearch.findAvailable(account.getUuid()));
    }

}

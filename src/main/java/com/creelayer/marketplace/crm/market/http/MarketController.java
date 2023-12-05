package com.creelayer.marketplace.crm.market.http;

import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.market.core.incoming.MarketManage;
import com.creelayer.marketplace.crm.market.core.incoming.MarketSearch;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.projection.MarketSearchResult;
import com.creelayer.marketplace.crm.market.http.dto.CreateMarketRequest;
import com.creelayer.marketplace.crm.market.http.dto.MarketSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import com.creelayer.marketplace.crm.market.http.mapper.MarketMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("market")
public class MarketController {

    private MarketManage marketManage;

    private MarketSearch marketSearch;

    private MarketMapper mapper;

    @GetMapping("")
    public Page<MarketSearchResult> index(
            @AuthenticationPrincipal Account account,
            MarketSearchFilter filter,
            @PageableDefault Pageable pageable
    ) {
        return marketSearch.search(mapper.map(account.getUuid(), filter), pageable, MarketSearchResult.class);
    }

    @PreAuthorize("hasPermission(#market, 'owner')")
    @GetMapping("{market}")
    public Market view(@PathVariable Market market) {
        return market;
    }

    @PostMapping("")
    public Market create(@AuthenticationPrincipal Account account, @RequestBody CreateMarketRequest request) {
        return marketManage.create(mapper.map(account.getUuid() ,request));
    }

    @PreAuthorize("hasPermission(#market, 'owner')")
    @PutMapping("{market}")
    public Market update(@PathVariable Market market, @RequestBody UpdateMarketRequest request) {
        return marketManage.update(mapper.map(market.getUuid(), request));
    }

    @PreAuthorize("hasPermission(#market, 'owner')")
    @DeleteMapping("{market}")
    public void delete(@PathVariable Market market) {
        marketManage.remove(market.getUuid());
    }
}

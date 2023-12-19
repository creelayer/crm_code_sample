package com.creelayer.marketplace.crm.market.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.market.core.incoming.MarketManage;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.market.core.projection.MarketDetailView;
import com.creelayer.marketplace.crm.market.core.projection.MarketSearchResult;
import com.creelayer.marketplace.crm.market.core.query.MarketSearchQuery;
import com.creelayer.marketplace.crm.market.http.dto.CreateMarketRequest;
import com.creelayer.marketplace.crm.market.http.dto.MarketSearchFilter;
import com.creelayer.marketplace.crm.market.http.dto.UpdateMarketRequest;
import com.creelayer.marketplace.crm.market.http.mapper.MarketMapper;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("market")
public class MarketController {

    private QueryHandler<MarketSearchQuery, Page<MarketSearchResult>> search;

    private MarketRepository marketRepository;

    private MarketManage marketManage;

    private MarketMapper mapper;

    @GetMapping("")
    public Page<MarketSearchResult> index(
            @AuthenticationPrincipal Account account,
            MarketSearchFilter filter
    ) {
        return search.ask(mapper.map(account.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#market, 'Market', 'owner')")
    @GetMapping("{market}")
    public MarketDetailView view(@PathVariable UUID market) {
        return marketRepository.findByUuid(market, MarketDetailView.class)
                .orElseThrow(() -> new NotFoundException("Market not found"));
    }

    @ActivityLog(entity = "#account.uuid", data = "#request", type = "MARKET_CREATE")
    @PostMapping("")
    public UUID create(@AuthenticationPrincipal Account account, @RequestBody CreateMarketRequest request) {
        return marketManage.create(mapper.map(account.getUuid(), request));
    }

    @ActivityLog(entity = "#market", data = "#request", type = "MARKET_UPDATE")
    @PreAuthorize("hasPermission(#market, 'Market', 'owner')")
    @PutMapping("{market}")
    public void update(@PathVariable UUID market, @RequestBody UpdateMarketRequest request) {
        marketManage.update(mapper.map(market, request));
    }

    @PreAuthorize("hasPermission(#market, 'Market', 'owner')")
    @DeleteMapping("{market}")
    public void delete(@PathVariable UUID market) {
        marketManage.remove(market);
    }
}

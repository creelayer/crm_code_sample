package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeAnalytic;
import com.creelayer.marketplace.crm.promo.core.incoming.PromoCodeUsage;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeClientStatistic;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeDetail;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeAnalyticsQuery;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeSearchQuery;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchUserResult;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeSearchFilter;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoCodeMapper;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("PromoCodeApi")
@RequestMapping("v1/promo/code")
public class ApiCodeController {

    private final PromoCodeUsage promoUsage;

    private final PromoCodeAnalytic analytic;

    private QueryHandler<PromoCodeSearchQuery, Page<PromoCodeSearchUserResult>> search;

    private final PromoCodeMapper mapper;

    @GetMapping("")
    public Page<PromoCodeSearchUserResult> search(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @RequestHeader(name = "X-Client-Phone") PromoCodeClient client,
            @RequestParam(required = false) PromoCodeSearchFilter.State state
    ) {
        PromoCodeSearchFilter filter = new PromoCodeSearchFilter()
                .setClient(client.getUuid())
                .setState(state);
        return search.ask(mapper.map(realm.getUuid(), filter));
    }

    @PostMapping("{code}")
    public PromoCodeDetail code(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String code) {
        return promoUsage.review(new Realm(realm.getUuid()), code);
    }

    @GetMapping("/statistic")
    public PromoCodeClientStatistic statistic(
            @RequestHeader(name = "X-Client-Phone") PromoCodeClient client
    ) {
        return analytic.clientStatistic(new PromoCodeAnalyticsQuery(client.getUuid()));
    }

}

package com.creelayer.marketplace.crm.promo.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.promo.core.model.PromoCodeClient;
import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeClientStatistic;
import com.creelayer.marketplace.crm.promo.core.query.PromoCodeAnalyticsQuery;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeCheckDetailResponse;
import com.creelayer.marketplace.crm.promo.core.projection.PromoCodeSearchUserResult;
import com.creelayer.marketplace.crm.promo.http.dto.PromoCodeSearchFilter;
import com.creelayer.marketplace.crm.promo.core.PromoCodeService;
import com.creelayer.marketplace.crm.promo.http.mapper.PromoCodeMapper;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("PromoCodeApi")
@RequestMapping("v1/promo/code")
public class ApiCodeController {

    private final PromoCodeService promoCodeService;

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeMapper mapper;

    @GetMapping("")
    public Page<PromoCodeSearchUserResult> search(
            @RequestHeader("X-Market-Identity") RealmIdentity realm,
            @RequestHeader(name = "X-Client-Phone") PromoCodeClient client,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC, value = 30) Pageable pageable,
            @RequestParam(required = false) PromoCodeSearchFilter.State state
    ) {
        PromoCodeSearchFilter filter = new PromoCodeSearchFilter(client.getUuid(), state);
        return promoCodeRepository.search(new Realm(realm.getUuid()), mapper.map(filter), pageable, PromoCodeSearchUserResult.class);
    }

    @PostMapping("{code}")
    public PromoCodeCheckDetailResponse code(@RequestHeader("X-Market-Identity") RealmIdentity realm, @PathVariable String code) {
        return mapper.map(promoCodeService.getCode(new Realm(realm.getUuid()), code));
    }

    @GetMapping("/statistic")
    public PromoCodeClientStatistic statistic(
            @RequestHeader(name = "X-Client-Phone") PromoCodeClient client
    ) {
        return promoCodeRepository.clientStatistic(new PromoCodeAnalyticsQuery(client.getUuid()));
    }

}

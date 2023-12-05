package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.CheckoutService;
import com.creelayer.marketplace.crm.order.http.dto.CheckoutRequest;
import com.creelayer.marketplace.crm.order.http.mapper.CheckoutMapper;
import com.creelayer.marketplace.crm.order.http.dto.OrderReviewResponse;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.model.Realm;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("v1/checkout")
public class CheckoutController {

    private CheckoutService checkout;

    private CheckoutMapper checkoutMapper;

    @GetMapping("{order}")
    public OrderReviewResponse view(@PathVariable Order order) {
        return checkoutMapper.map(order);
    }

    @GetMapping("{order}/paymenturl")
    public String externalPaymentUrl(@PathVariable Order order) {
        return checkout.getExternalPaymentUrl(order.getUuid());
    }

    @PostMapping("")
    public OrderReviewResponse checkout(
            @AuthenticationPrincipal RealmIdentity realm,
            @Valid @Pattern(regexp = "^[0-9]{12}$") @RequestHeader(name = "X-Client-Phone", required = false) String phone,
            @RequestBody @Valid CheckoutRequest request
    ) {
        return checkoutMapper.map(checkout.checkout(new Realm(realm.getUuid()), checkoutMapper.map(phone, request)));
    }
}

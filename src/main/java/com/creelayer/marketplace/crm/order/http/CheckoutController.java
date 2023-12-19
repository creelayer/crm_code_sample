package com.creelayer.marketplace.crm.order.http;

import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.CheckoutService;
import com.creelayer.marketplace.crm.order.core.outgoing.OrderRepository;
import com.creelayer.marketplace.crm.order.core.projection.CheckoutReview;
import com.creelayer.marketplace.crm.order.http.dto.CheckoutRequest;
import com.creelayer.marketplace.crm.order.http.mapper.CheckoutMapper;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("v1/checkout")
public class CheckoutController {

    private CheckoutService checkout;

    private CheckoutMapper checkoutMapper;

    private OrderRepository orderRepository;

    @GetMapping("{order}")
    public CheckoutReview view(@PathVariable UUID order) {
        return orderRepository.findByUuid(order, CheckoutReview.class)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @GetMapping("{order}/paymenturl")
    public String externalPaymentUrl(@PathVariable UUID order) {
        return checkout.getExternalPaymentUrl(order);
    }

    @PostMapping("")
    public UUID checkout(
            @AuthenticationPrincipal RealmIdentity realm,
            @Valid @Pattern(regexp = "^[0-9]{12}$") @RequestHeader(name = "X-Client-Phone", required = false) String phone,
            @RequestBody @Valid CheckoutRequest request
    ) {
        return checkout.checkout(checkoutMapper.map(realm, phone, request));
    }
}

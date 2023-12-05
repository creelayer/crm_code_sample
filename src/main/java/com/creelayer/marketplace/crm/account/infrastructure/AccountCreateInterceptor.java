package com.creelayer.marketplace.crm.account.infrastructure;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.core.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.*;

@AllArgsConstructor
public class AccountCreateInterceptor implements HandlerInterceptor {

    private AccountRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt jwt) {

            UUID uuid = UUID.fromString(jwt.getSubject());

            Account account = accountRepository.findById(uuid).orElseGet(() -> {
                Account entity = new Account(uuid, jwt.getClaim("email"));
                entity.setClientId(jwt.getClaim("clientId"));
                entity.setFullName(jwt.getClaim("name"));
                return accountRepository.save(entity);
            });

            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(account, null, authorities)
            );
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

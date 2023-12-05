package com.creelayer.marketplace.crm.app.config;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.infrastructure.AccountCreateInterceptor;
import com.creelayer.marketplace.crm.secutiry.CompoundDomainAwarePermissionEvaluator;
import com.creelayer.marketplace.crm.secutiry.ResourceGrantedAuthority;
import com.creelayer.marketplace.crm.market.core.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MarketService marketService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccountCreateInterceptor(accountRepository));
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(List<PermissionEvaluator> permissionEvaluators) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CompoundDomainAwarePermissionEvaluator(permissionEvaluators));
        return handler;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .httpBasic()
                .and()
                .securityMatcher("/v1/**")
                .authorizeHttpRequests()
                .anyRequest().hasRole("MARKET_API")
                .and()
                .formLogin().disable()
                .logout().disable()
                .userDetailsService(marketService)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        return http.build();
    }


    @Bean
    public SecurityFilterChain adminApiFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(source -> {
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (source.hasClaim("scope")) {
                String[] scopes = source.getClaimAsString("scope").split(" ");
                authorities.addAll(Arrays.stream(scopes)
                        .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                        .toList());
            }

            if (source.hasClaim("realm_access")) {
                Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
                if (realmAccess != null) {
                    List<String> roles = (List<String>) realmAccess.get("roles");
                    authorities.addAll(roles.stream()
                            .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                            .toList());
                }
            }

            if (source.hasClaim("authorization") && source.getClaimAsMap("authorization").containsKey("permissions")) {

                List<Map<String, Object>> permissions = (List<Map<String, Object>>) source.getClaimAsMap("authorization").get("permissions");

                for (Map<String, Object> permission : permissions) {
                    List<String> scopes = (List<String>) permission.get("scopes");
                    String resourceName = (String) permission.get("rsname");
                    authorities.addAll(scopes.stream()
                            .map(scope -> new ResourceGrantedAuthority(resourceName, scope))
                            .toList());
                }
            }

            return authorities;
        });
        return jwtConverter;
    }
}

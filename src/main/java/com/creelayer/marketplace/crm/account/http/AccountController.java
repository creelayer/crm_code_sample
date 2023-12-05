package com.creelayer.marketplace.crm.account.http;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.core.model.Account;
import com.creelayer.marketplace.crm.account.core.projection.AccountDetail;
import com.creelayer.marketplace.crm.common.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("account")
public class AccountController {

    private AccountRepository accountRepository;


    @GetMapping("/search")
    public AccountDetail findManager(@RequestParam String search, @AuthenticationPrincipal Account account) {
        if (search.equals(account.getEmail()))
            throw  new NotFoundException("Account not found");
        return accountRepository.findByEmail(search).orElseThrow(() -> new NotFoundException("Account not found"));
    }

}

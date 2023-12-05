package com.creelayer.marketplace.crm.account.core.incoming;

import com.creelayer.marketplace.crm.account.core.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends AccountSearch {
    Optional<Account> findById(UUID uuid);

    Account save(Account account);
}

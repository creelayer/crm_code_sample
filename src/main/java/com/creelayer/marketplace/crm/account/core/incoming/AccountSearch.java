package com.creelayer.marketplace.crm.account.core.incoming;

import com.creelayer.marketplace.crm.account.core.projection.AccountDetail;

import java.util.Optional;

public interface AccountSearch {
    Optional<AccountDetail> findByEmail(String email);

}

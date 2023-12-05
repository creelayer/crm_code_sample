package com.creelayer.marketplace.crm.account.infrastructure.persistence;

import com.creelayer.marketplace.crm.account.core.incoming.AccountRepository;
import com.creelayer.marketplace.crm.account.core.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, UUID>, AccountRepository {
}

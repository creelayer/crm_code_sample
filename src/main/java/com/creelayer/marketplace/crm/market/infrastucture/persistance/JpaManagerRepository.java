package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaManagerRepository extends JpaRepository<Manager, UUID>, ManagerRepository {

}

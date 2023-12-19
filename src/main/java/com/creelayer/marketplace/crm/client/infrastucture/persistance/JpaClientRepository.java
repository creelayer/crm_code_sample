package com.creelayer.marketplace.crm.client.infrastucture.persistance;

import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.client.core.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, UUID>, ClientRepository {

}

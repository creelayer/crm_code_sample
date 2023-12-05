package com.creelayer.marketplace.crm.client.infrastucture.persistance;

import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.projection.ClientSearchResult;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationProjectionExecutor<Client>, ClientRepository {

    default Page<ClientSearchResult> search(Realm realm, ClientSearchQuery query, Pageable pageable) {
        return findAll(new ClientSearchSpecification(realm, query), pageable, ClientSearchResult.class);
    }

}

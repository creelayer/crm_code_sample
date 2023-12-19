package com.creelayer.marketplace.crm.client.infrastucture.persistance;

import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.projection.ClientSearchResult;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaClientSearch extends
        org.springframework.data.repository.Repository<Client, UUID>,
        JpaSpecificationProjectionExecutor<Client>,
        QueryHandler<ClientSearchQuery, Page<ClientSearchResult>> {

    @Override
    default Page<ClientSearchResult> ask(ClientSearchQuery query){
        return findAll(
                new ClientSearchSpecification(new Realm(query.getRealm()), query),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                ClientSearchResult.class
        );
    }
}

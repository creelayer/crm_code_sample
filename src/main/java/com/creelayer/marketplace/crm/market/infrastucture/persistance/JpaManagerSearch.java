package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.projection.ManagerSearchResult;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaManagerSearch extends
        org.springframework.data.repository.Repository<Manager, UUID>,
        JpaSpecificationProjectionExecutor<Manager>,
        QueryHandler<ManagerSearchQuery, Page<ManagerSearchResult>> {

    @Override
    default Page<ManagerSearchResult> ask(ManagerSearchQuery query){
        return findAll(
                new ManagerSearchSpecification(query),
                        PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt")),
                        ManagerSearchResult.class
                );
    }
}

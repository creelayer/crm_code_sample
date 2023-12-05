package com.creelayer.marketplace.crm.market.infrastucture.persistance;

import com.creelayer.marketplace.crm.common.jpaSpecificationProjection.JpaSpecificationProjectionExecutor;
import com.creelayer.marketplace.crm.market.core.model.Manager;
import com.creelayer.marketplace.crm.market.core.outgoing.ManagerRepository;
import com.creelayer.marketplace.crm.market.core.projection.ManagerDetail;
import com.creelayer.marketplace.crm.market.core.query.ManagerSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaManagerRepository extends JpaRepository<Manager, UUID>, JpaSpecificationProjectionExecutor<Manager>, ManagerRepository {

    @Override
    @Query("SELECT m FROM Manager m WHERE m.account.uuid = :account AND m.market.uuid = :market")
    Optional<ManagerDetail> getDetail(UUID account, UUID market);

    @Override
    default <R> Page<R> search(ManagerSearchQuery query, Pageable pageable, Class<R> rClass){
        return findAll(new ManagerSearchSpecification(query), pageable, rClass);
    }
}

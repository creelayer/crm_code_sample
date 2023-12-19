package com.creelayer.marketplace.crm.promo.infrastucture.persistance;

import com.creelayer.marketplace.crm.promo.core.outgoing.PromoCodeRepository;
import com.creelayer.marketplace.crm.promo.core.model.Realm;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPromoCodeRepository extends JpaRepository<PromoCode, UUID>, PromoCodeRepository {

    @Query("SELECT c FROM PromoCode c JOIN c.group g WHERE g.realm = :realm AND c.code = :code AND c.deletedAt IS NULL ")
    Optional<PromoCode> findPromoCode(Realm realm, String code);

    @Query("SELECT case when count(c)> 0 then true else false end FROM PromoCode c JOIN c.group g WHERE g.realm = :realm AND c.code = :code AND c.deletedAt IS NULL ")
    boolean existsPromoCode(Realm realm, String code);

    @Override
    default void save(Collection<PromoCode> entities){
        saveAll(entities);
    }
}

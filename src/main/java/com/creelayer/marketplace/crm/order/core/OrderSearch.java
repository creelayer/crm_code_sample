package com.creelayer.marketplace.crm.order.core;

import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.order.core.query.OrderSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderSearch {
    <T> Page<T> search(RealmIdentity realm, OrderSearchQuery query, Pageable pageable, Class<T> projection);

}

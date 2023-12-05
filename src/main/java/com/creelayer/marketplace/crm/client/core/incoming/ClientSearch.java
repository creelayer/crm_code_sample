package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.projection.ClientSearchResult;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientSearch {
    Page<ClientSearchResult> search(Realm realm, ClientSearchQuery query, Pageable pageable);

}

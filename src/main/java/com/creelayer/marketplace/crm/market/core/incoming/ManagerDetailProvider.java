package com.creelayer.marketplace.crm.market.core.incoming;

import com.creelayer.marketplace.crm.market.core.projection.ManagerView;

import java.util.UUID;

public interface ManagerDetailProvider {

    ManagerView detail(UUID manager);

}

package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.command.ClientDetailCommand;
import com.creelayer.marketplace.crm.client.core.projection.ClientDetail;

public interface ClientDetailDistributor {
     ClientDetail getClientDetail(ClientDetailCommand command);
}

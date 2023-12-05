package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;
import com.creelayer.marketplace.crm.client.core.model.Client;

public interface ClientManage {

    Client manage(UpdateClientCommand command);
}

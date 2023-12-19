package com.creelayer.marketplace.crm.client.core.incoming;

import com.creelayer.marketplace.crm.client.core.command.UpdateClientApiCommand;
import com.creelayer.marketplace.crm.client.core.command.UpdateClientCommand;

public interface ClientManage {

    void manage(UpdateClientApiCommand command);

    void manage(UpdateClientCommand command);
}

package com.creelayer.marketplace.crm.loyalty.core.incoming;

import com.creelayer.marketplace.crm.loyalty.core.command.RegistrationPromoSettingCommand;

public interface RegistrationPromoManager {
    void setting(RegistrationPromoSettingCommand command);
}

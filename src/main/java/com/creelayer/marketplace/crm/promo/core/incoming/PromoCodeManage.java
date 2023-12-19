package com.creelayer.marketplace.crm.promo.core.incoming;


import com.creelayer.marketplace.crm.promo.core.command.GeneratePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.command.UpdatePromoCodeCommand;
import com.creelayer.marketplace.crm.promo.core.model.PromoCode;

import java.util.UUID;

public interface PromoCodeManage extends PromoConditionSupport<PromoCode> {

  void generate(GeneratePromoCodeCommand command);

  void update(UpdatePromoCodeCommand command);

  void remove(UUID uuid);
}

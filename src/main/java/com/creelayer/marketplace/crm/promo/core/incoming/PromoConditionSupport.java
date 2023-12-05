package com.creelayer.marketplace.crm.promo.core.incoming;


import com.creelayer.marketplace.crm.promo.core.command.ManagePromoConditionCommand;

public interface PromoConditionSupport<T> {

  T update(ManagePromoConditionCommand command);

}

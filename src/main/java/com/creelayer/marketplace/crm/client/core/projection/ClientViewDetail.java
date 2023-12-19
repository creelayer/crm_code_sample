package com.creelayer.marketplace.crm.client.core.projection;

import com.creelayer.marketplace.crm.client.core.model.Realm;

import java.util.UUID;


public interface ClientViewDetail  {

     UUID getUuid();


     Realm getRealm();

     String getPhone();


     String getName();

     String getEmail();
}

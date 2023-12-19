package com.creelayer.marketplace.crm.client.core.command;

import java.util.UUID;

public record UpdateClientApiCommand(UUID realm, String phone, String email, String name) {

}

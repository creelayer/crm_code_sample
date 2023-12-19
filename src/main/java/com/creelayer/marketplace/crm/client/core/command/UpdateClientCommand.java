package com.creelayer.marketplace.crm.client.core.command;

import java.util.UUID;

public record UpdateClientCommand(UUID client, String email, String name) {

}

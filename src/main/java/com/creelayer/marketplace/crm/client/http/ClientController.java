package com.creelayer.marketplace.crm.client.http;

import com.creelayer.marketplace.crm.client.core.incoming.ClientBalanceDistributor;
import com.creelayer.marketplace.crm.client.core.incoming.ClientSearch;
import com.creelayer.marketplace.crm.client.core.command.BalanceResolveCommand;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import com.creelayer.marketplace.crm.client.http.mapper.ClientMapper;
import com.creelayer.marketplace.crm.client.core.model.Realm;
import com.creelayer.marketplace.crm.client.core.ClientService;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.http.dto.ClientSearchFilter;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("client")
public class ClientController {

    private ClientSearch searchQueryHandler;

    private ClientBalanceDistributor balanceResolver;

    private ClientService clientService;

    private ClientMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'client_read')")
    @GetMapping("")
    public Page<?> index(@RequestHeader("X-Market-Identity") RealmIdentity realm, ClientSearchFilter filter, Pageable pageable) {
        return searchQueryHandler.search(new Realm(realm.getUuid()), mapper.map(filter), pageable);
    }

    @PreAuthorize("hasPermission(#client.realm, 'client_read')")
    @GetMapping("{client}")
    public Client view(@PathVariable Client client) {
        return client;
    }

    @PreAuthorize("hasPermission(#client.realm, 'client_manage')")
    @PutMapping("{client}")
    public Client update(@PathVariable Client client, @Valid @RequestBody UpdateClientRequest request) {
        return clientService.manage(mapper.map(client, request));
    }

    @PreAuthorize("hasPermission(#client.realm, 'client_manage')")
    @GetMapping("{client}/balance")
    public Balance balance(@PathVariable Client client) {
        return balanceResolver.getClientBalance(new BalanceResolveCommand(client.getUuid()));
    }
}

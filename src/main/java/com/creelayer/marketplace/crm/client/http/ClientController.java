package com.creelayer.marketplace.crm.client.http;

import com.creelayer.activity.data.ActivityLog;
import com.creelayer.marketplace.crm.client.core.query.BalanceDetailQuery;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientRepository;
import com.creelayer.marketplace.crm.client.core.projection.ClientSearchResult;
import com.creelayer.marketplace.crm.client.core.projection.ClientViewDetail;
import com.creelayer.marketplace.crm.client.core.query.ClientSearchQuery;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import com.creelayer.marketplace.crm.client.http.mapper.ClientMapper;
import com.creelayer.marketplace.crm.client.core.ClientService;
import com.creelayer.marketplace.crm.client.http.dto.ClientSearchFilter;
import com.creelayer.marketplace.crm.common.NotFoundException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("client")
public class ClientController {

    private QueryHandler<ClientSearchQuery, Page<ClientSearchResult>> search;

    private QueryHandler<BalanceDetailQuery, Balance> balanceResolver;

    private ClientRepository clientRepository;

    private ClientService clientService;

    private ClientMapper mapper;

    @PreAuthorize("hasPermission(#realm, 'client_read')")
    @GetMapping("")
    public Page<?> index(@RequestHeader("X-Market-Identity") RealmIdentity realm, ClientSearchFilter filter) {
        return search.ask(mapper.map(realm.getUuid(), filter));
    }

    @PreAuthorize("hasPermission(#client, 'Client', 'client_read')")
    @GetMapping("{client}")
    public ClientViewDetail view(@PathVariable UUID client) {
        return clientRepository
                .findByUuid(client, ClientViewDetail.class)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    @ActivityLog(entity = "#client", data = "#request", type = "CLIENT_UPDATE")
    @PreAuthorize("hasPermission(#client, 'Client', 'client_manage')")
    @PutMapping("{client}")
    public void update(@PathVariable UUID client, @Valid @RequestBody UpdateClientRequest request) {
        clientService.manage(mapper.map(client, request));
    }

    @PreAuthorize("hasPermission(#client, 'Client', 'client_manage')")
    @GetMapping("{client}/balance")
    public Balance balance(@PathVariable UUID client) {
        return balanceResolver.ask(new BalanceDetailQuery(client));
    }
}

package com.creelayer.marketplace.crm.client.http;

import com.creelayer.marketplace.crm.client.core.command.ClientDetailCommand;
import com.creelayer.marketplace.crm.client.core.incoming.ClientDetailDistributor;
import com.creelayer.marketplace.crm.client.core.incoming.ClientManage;
import com.creelayer.marketplace.crm.client.core.projection.ClientDetail;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import com.creelayer.marketplace.crm.client.http.mapper.ClientMapper;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@AllArgsConstructor
@Validated
@RestController("ClientApi")
@RequestMapping("v1/client")
public class ApiClientController {

    private ClientManage clientManage;

    private ClientDetailDistributor clientDetail;

    private ClientMapper mapper;

    @GetMapping("identity")
    public ClientDetail identity(
            @RequestHeader(name = "X-Client-Phone") Client client,
            @RequestParam(required = false) boolean withBalance
    ) {
        return clientDetail.getClientDetail(new ClientDetailCommand(client.getUuid(), withBalance));
    }

    @PutMapping("update")
    public Client update(
            @RequestHeader(name = "X-Client-Phone") Client client,
            @Valid @RequestBody UpdateClientRequest request
            ) {
        return clientManage.manage(mapper.map(client, request));
    }
}

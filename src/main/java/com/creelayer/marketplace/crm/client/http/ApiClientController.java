package com.creelayer.marketplace.crm.client.http;

import com.creelayer.marketplace.crm.client.core.query.ClientDetailQuery;
import com.creelayer.marketplace.crm.client.core.incoming.ClientManage;
import com.creelayer.marketplace.crm.client.core.projection.ClientDetail;
import com.creelayer.marketplace.crm.client.http.dto.UpdateClientRequest;
import com.creelayer.marketplace.crm.client.http.mapper.ClientMapper;
import com.creelayer.marketplace.crm.common.handler.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@AllArgsConstructor
@Validated
@RestController("ClientApi")
@RequestMapping("v1/client")
public class ApiClientController {

    private ClientManage clientManage;

    private QueryHandler<ClientDetailQuery, ClientDetail> clientDetail;

    private ClientMapper mapper;

    @GetMapping("identity")
    public ClientDetail identity(
            @RequestHeader("X-Market-Identity") UUID realm,
            @RequestHeader(name = "X-Client-Phone") String phone,
            @RequestParam(required = false) boolean withBalance
    ) {
        return clientDetail.ask(new ClientDetailQuery(realm, phone, withBalance));
    }

    @PutMapping("update")
    public void update(
            @RequestHeader("X-Market-Identity") UUID realm,
            @RequestHeader(name = "X-Client-Phone") String phone,
            @Valid @RequestBody UpdateClientRequest request
            ) {
        clientManage.manage(mapper.map(realm, phone, request));
    }
}

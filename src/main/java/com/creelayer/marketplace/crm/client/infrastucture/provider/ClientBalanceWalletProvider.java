package com.creelayer.marketplace.crm.client.infrastucture.provider;

import com.creelayer.wallet.client.BalanceApi;
import com.creelayer.wallet.client.BalanceKey;
import com.creelayer.wallet.client.WalletAccess;
import com.creelayer.wallet.client.WalletApiBuilder;
import com.creelayer.wallet.client.exception.WalletException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.client.core.model.Client;
import com.creelayer.marketplace.crm.client.core.outgoing.ClientBalanceProvider;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.client.core.model.ClientBalanceKey;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.client.core.exception.ClientBalanceException;
import com.creelayer.wallet.client.dto.BalanceSettings;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public final class ClientBalanceWalletProvider implements ClientBalanceProvider {

    private final MarketRepository marketRepository;

    private final WalletApiBuilder builder;

    @Setter
    private String defaultBalanceDescription = "Client bonus balance";

    @Override
    public ClientBalanceKey createBalanceKey(Client client) {

        WalletAccess access = getWalletAccess(client);

        return createBalanceKey(access, new BalanceSettings(defaultBalanceDescription));
    }

    @Override
    public Balance getBalance(Client client) {

        BalanceKey key = getBalanceKey(client);

        if (key == null)
            return null;

        WalletAccess access = getWalletAccess(client);

        return getBalance(access, key);
    }

    private BalanceKey getBalanceKey(Client client) {

        ClientBalanceKey key = client.getBalanceAccessKey();

        if (key == null)
            return null;

        return new BalanceKey(key.getUuid(), key.getSecret());
    }

    private WalletAccess getWalletAccess(Client client) {

        RealmIdentity identity = client.getRealm();
        Market market = marketRepository.findById(identity.getUuid()).orElseThrow();
        Market.Wallet wallet = market.getWallet();

        if (wallet == null)
            throw new IllegalStateException("Invalid wallet configuration");

        return wallet.access;
    }

    private ClientBalanceKey createBalanceKey(WalletAccess access, BalanceSettings settings) {
        try {
            BalanceKey key = builder.withAccess(access).build(BalanceApi.class).create(settings).content;
            return new ClientBalanceKey(
                    key.uuid,
                    key.secret
            );
        } catch (WalletException e) {
            throw new ClientBalanceException("Can't create wallet");
        }
    }

    private Balance getBalance(WalletAccess access, BalanceKey key) {

        try {
            com.creelayer.wallet.client.dto.Balance balance = builder.withAccess(access).build(BalanceApi.class).view(key.uuid).content;
            return new Balance(
                    balance.uuid,
                    Balance.Currency.valueOf(balance.currency),
                    balance.amount
            );
        } catch (WalletException e) {
            throw new ClientBalanceException("Can't secutiry to wallet");
        }
    }
}

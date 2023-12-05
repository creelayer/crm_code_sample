package com.creelayer.marketplace.crm.order.infrastucture.provider;

import com.creelayer.wallet.client.InvoiceApi;
import com.creelayer.wallet.client.WalletApiBuilder;
import com.creelayer.wallet.client.exception.WalletException;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentity;
import com.creelayer.marketplace.crm.common.reaml.RealmIdentityProvider;
import com.creelayer.marketplace.crm.client.core.incoming.ClientBalanceDistributor;
import com.creelayer.marketplace.crm.client.core.command.BalanceResolveCommand;
import com.creelayer.marketplace.crm.client.core.model.Balance;
import com.creelayer.marketplace.crm.market.core.model.Market;
import com.creelayer.marketplace.crm.market.core.outgoing.MarketRepository;
import com.creelayer.marketplace.crm.order.core.exception.OrderInvoiceException;
import com.creelayer.marketplace.crm.order.core.model.Order;
import com.creelayer.marketplace.crm.order.core.OrderInvoiceProvider;
import com.creelayer.marketplace.crm.order.core.model.OrderInvoice;
import com.creelayer.marketplace.crm.order.core.model.OrderItem;
import com.creelayer.wallet.client.dto.Invoice;
import com.creelayer.wallet.client.dto.InvoiceSettings;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public final class CheckoutOrderInvoiceProvider implements OrderInvoiceProvider {

    private final MarketRepository marketRepository;

    private final ClientBalanceDistributor clientBalanceDistributor;

    private final WalletApiBuilder walletBuilder;

    private final RealmIdentityProvider realmProvider;

    @Setter
    private BiFunction<Market, Order, String> descriptionMaker = (m, o) -> "Marketplace order";

    @Override
    public OrderInvoice createInvoice(Order order) {
        Market market = getMarket();
        InvoiceSettings settings = getSettings(market, order);
        return createInvoice(market, settings);
    }

    private InvoiceSettings getSettings(Market market, Order order) {

        InvoiceSettings settings = new InvoiceSettings();
        settings.externalId = getExternalId(order);
        settings.amount = order.getSummary().getSummary();
        settings.description = descriptionMaker.apply(market, order);
        settings.statusUrl = getSuccessUrl(market, order);
        settings.meta = getMeta(order);
        settings.payout = createPayoutIfRequired(order);
        return settings;
    }

    private OrderInvoice createInvoice(Market market, InvoiceSettings settings) {

        try {
            Market.Wallet wallet = market.getWallet();

            if (wallet == null)
                throw new IllegalStateException("Invalid wallet configuration");

            Invoice invoice = walletBuilder
                    .withAccess(wallet.access)
                    .build(InvoiceApi.class)
                    .create(wallet.balance, settings)
                    .getContent();
            return new OrderInvoice(invoice.uuid);
        } catch (WalletException e) {
            throw new OrderInvoiceException("Can't create invoice on remote wallet service");
        }
    }

    private List<InvoiceSettings.Payout> createPayoutIfRequired(Order order) {

        if (order.getPayouts().isEmpty())
            return null;

        Balance balance = clientBalanceDistributor.getClientBalance(new BalanceResolveCommand(order.getCustomer().getUuid(), true));

        return order.getPayouts()
                .stream()
                .map(e -> new InvoiceSettings.Payout(balance.getUuid(), e.getAmount(), e.getDescription()))
                .collect(Collectors.toList());
    }

    private Market getMarket() {
        RealmIdentity identity = realmProvider.resolve();
        return marketRepository.findById(identity.getUuid()).orElseThrow();
    }

    private String getExternalId(Order order) {
        if (order.getUuid() == null)
            throw new InvalidParameterException("Order uuid is required");

        return order.getUuid().toString();
    }

    private String getSuccessUrl(Market market, Order order) {
        return market.getUrl() + "/checkout/" + order.getUuid();
    }

    private Map<String, String> getMeta(Order order) {
        Map<String, String> meta = new HashMap<>();
        meta.put("order_id", order.getUuid().toString());
        meta.put("name", order.getContact().getName());
        meta.put("phone", order.getContact().getPhone());
        meta.put("email", order.getContact().getEmail());

        for (OrderItem item : order.getItems())
            meta.put("product_item_" + item.getSku(), item.toString());

        return meta;
    }
}

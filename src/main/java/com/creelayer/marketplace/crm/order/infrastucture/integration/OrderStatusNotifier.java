package com.creelayer.marketplace.crm.order.infrastucture.integration;

import com.creelayer.setting.client.Setting;
import com.creelayer.setting.client.SettingApi;
import com.creelayer.mosquito.client.MosquitoMessengerApi;
import com.creelayer.mosquito.client.dto.ChannelMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@ConditionalOnProperty(value = "crm.order.inform", havingValue = "true")
public class OrderStatusNotifier {

    @Autowired
    private SettingApi<Setting> sip;

    @Autowired
    private DataSource dataSource;

    @Bean
    public MessageChannel newOrders() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel noReactionOrders() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "newOrders", poller = @Poller(fixedDelay = "10000"))
    public MessageSource<?> ordersInbound() {
        String query = "SELECT count(*), orders.realm FROM orders " +
                "WHERE orders.status = 'NEW' AND orders.updated_at >= (SELECT last_run_at FROM poller WHERE name='order') " +
                "GROUP BY orders.realm";
        JdbcPollingChannelAdapter adapter = new JdbcPollingChannelAdapter(dataSource, query);
        adapter.setUpdateSql("UPDATE poller SET last_run_at = now() WHERE name='order'");
        return adapter;
    }

    @Bean
    @InboundChannelAdapter(value = "noReactionOrders", poller = @Poller(fixedDelay = "300000"))
    public MessageSource<?> noReactionOrdersInbound() {
        return new JdbcPollingChannelAdapter(dataSource, "SELECT count(*), orders.realm FROM orders " +
                "WHERE orders.status = 'NEW' AND orders.created_at < now() - interval '10 minute' AND orders.created_at > now() - interval '1 day' " +
                "GROUP BY orders.realm");
    }

    @Bean
    @ServiceActivator(inputChannel = "newOrders")
    public MessageHandler newOrderMessage(MosquitoMessengerApi api) {
        return message -> {
            
            List<Map<String, Object>> data = (List<Map<String, Object>>) message.getPayload();
            data.forEach(item -> {
                UUID mid = (UUID) item.get("realm");
                Setting setting = sip.one(mid.toString(), "market:integration:wallet").orElse(null);

                if (setting == null)
                    return;

                Map<String, String> keys = (Map<String, String>)setting.getValue();
                String channelUuid = keys.get("channel:order:uuid");
                String messageUuid = keys.get("message:order:uuid");
                String count = String.valueOf(item.get("count"));
                api.send(channelUuid, new ChannelMessage(messageUuid, Map.of("count", count)));
            });
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "noReactionOrders")
    public MessageHandler noReactionOrderMessage(MosquitoMessengerApi api) {
        return message -> {
            List<Map<String, Object>> data = (List<Map<String, Object>>) message.getPayload();
            data.forEach(item -> {
                UUID mid = (UUID) item.get("realm");
                Setting setting = sip.one(mid.toString(), "market:integration:wallet").orElse(null);

                if (setting == null)
                    return;

                Map<String, String> keys = (Map<String, String>)setting.getValue();
                String channelUuid = keys.get("channel:order:uuid");
                String messageUuid = keys.get("message:order:wait:uuid");
                String count = String.valueOf(item.get("count"));
                api.send(channelUuid, new ChannelMessage(messageUuid, Map.of("count", count)));
            });
        };
    }
}

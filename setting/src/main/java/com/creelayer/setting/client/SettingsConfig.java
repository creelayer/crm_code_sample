package com.creelayer.setting.client;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.net.MalformedURLException;

@Configuration
public class SettingsConfig {

    @Value("${couch.datasource.url}")
    private String url;

    @Value("${couch.datasource.username}")
    private String username;

    @Value("${couch.datasource.password}")
    private String password;

    @Value("${couch.datasource.db.settings}")
    private String database;

    @Bean
    @ConditionalOnMissingBean(CouchDbConnector.class)
    public CouchDbConnector SettingsCouchDbConnector() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url(url)
                .username(username)
                .password(password)
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        return new StdCouchDbConnector(database, dbInstance);
    }
}

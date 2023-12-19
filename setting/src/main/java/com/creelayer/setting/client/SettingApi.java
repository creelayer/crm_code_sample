package com.creelayer.setting.client;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@AllArgsConstructor
public class SettingApi<T extends Setting> {

    public final static char END = '\ufff0';

    private final Class<T> typeParameterClass;

    private final CouchDbConnector connector;

    private final String designDocId;

    @Setter
    private String viewName = "all";

    public Optional<T> one(String ...key) {
        ViewQuery query = new ViewQuery()
                .designDocId(designDocId)
                .viewName(viewName)
                .key(key);

        List<T> settings = connector.queryView(query, typeParameterClass);

        if(settings.size() > 1)
            throw new IllegalStateException("Return result more than one");

        return !settings.isEmpty() ? Optional.of(settings.get(0)) : Optional.empty();
    }

    public Optional<T> one(KeyIdentity identity) {
        ViewQuery query = new ViewQuery()
                .designDocId(designDocId)
                .viewName(viewName)
                .key(identity.key());

        List<T> settings = connector.queryView(query, typeParameterClass);

        if(settings.size() > 1)
            throw new IllegalStateException("Return result more than one");

        return !settings.isEmpty() ? Optional.of(settings.get(0)) : Optional.empty();
    }

    public List<T> range(KeyIdentity start, KeyIdentity end) {
        ViewQuery query = new ViewQuery()
                .designDocId(designDocId)
                .viewName(viewName)
                .startKey(start.key())
                .endKey(end.key());

        return connector.queryView(query, typeParameterClass);
    }

    public void create(T setting) {
        connector.create(setting);
    }

    public void update(T setting) {
        connector.update(setting);
    }

    public void save(T setting) {
        if (setting.id == null)
            create(setting);
        else
            update(setting);
    }

    public void delete(T setting) {
        connector.delete(setting);
    }

    public void put(T setting) {

        T exist = one(setting).orElse(null);

        if (exist == null) {
            connector.create(setting);
            return;
        }

        setting.id = exist.id;
        setting.revision = exist.revision;

        connector.update(setting);
    }

    public void put(List<T> settings) {
        settings.forEach(this::put);
    }

}

package com.creelayer.setting.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties({"id", "revision"})
public class Setting implements KeyIdentity {

    public enum Type {
        NUMBER, STRING, JSON
    }

    public enum Access {
        PUBLIC, PRIVATE
    }

    @JsonProperty("_id")
    public String id;

    @JsonProperty("_rev")
    public String revision;

    public String mid;

    public String name;

    public String description;

    public boolean editable = true;

    public boolean deletable = true;

    public Access access = Access.PUBLIC;

    public Type type = Type.STRING;

    private Object value;

    public Setting(String name) {
        this.name = name;
    }

    public Setting(UUID mid, String name) {
        this.mid = mid.toString();
        this.name = name;
    }

    public Setting(UUID mid, String name, Object value) {
        this.mid = mid.toString();
        this.name = name;
        this.value = value;
    }

    public Object key() {
        return mid == null ? name : new String[]{mid, name};
    }

    public void setValue(Object value) {
        if (value instanceof Number)
            this.type = Type.NUMBER;
        else if (value instanceof String)
            this.type = Type.STRING;
        else
            this.type = Type.JSON;

        this.value = value;
    }

    public <T> T getValue(Class<T> tClass) {
        return tClass.cast(value);
    }
}

package com.creelayer.keycloak.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class Attributes {

    private Map<String, List<String>> attributes = new HashMap<>();

    public Attributes(Map<String, List<String>> attributes) {
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    public void put(String name, List<String> value) {
        attributes.put(name, value);
    }

}

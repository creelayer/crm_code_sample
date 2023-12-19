package com.creelayer.keycloak.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@Getter
public class Resource {

    @JsonProperty("_id")
    public UUID id;

    public String name;
    public String type;
    public String displayName;
    public boolean ownerManagedAccess = true;
    @JsonProperty("resource_scopes")
    public List<String> scopes = new ArrayList<>();

}

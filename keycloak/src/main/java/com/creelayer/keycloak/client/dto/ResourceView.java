package com.creelayer.keycloak.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ResourceView {

    @JsonProperty("_id")
    public UUID id;

    public String name;
    public String type;
    public String displayName;
    public boolean ownerManagedAccess = true;
    @JsonProperty("resource_scopes")
    public List<Scope> scopes = new ArrayList<>();

    public static class Scope{
        public UUID id;
        public String name;
    }

}

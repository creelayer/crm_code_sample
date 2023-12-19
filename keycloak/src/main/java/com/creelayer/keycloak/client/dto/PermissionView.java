package com.creelayer.keycloak.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class PermissionView {

    public enum DecisionStrategy{
        AFFIRMATIVE
    }

    public UUID id;

    public String name;

    public String description;

    public DecisionStrategy decisionStrategy = DecisionStrategy.AFFIRMATIVE;

    public List<String> scopes = new ArrayList<>();

    public List<String> users = new ArrayList<>();

}

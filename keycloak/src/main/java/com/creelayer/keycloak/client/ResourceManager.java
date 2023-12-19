package com.creelayer.keycloak.client;

import com.creelayer.keycloak.client.dto.Permission;
import com.creelayer.keycloak.client.dto.PermissionView;
import com.creelayer.keycloak.client.dto.Resource;
import com.creelayer.keycloak.client.dto.ResourceView;
import com.creelayer.keycloak.client.exception.ConflictException;
import com.creelayer.keycloak.client.exception.NotFoundException;
import com.creelayer.keycloak.client.exception.ResourceException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ResourceManager {

    private ResourceApi resourceApi;

    private PolicyApi policyApi;

    public ResourceView getResource(String resourceName) {
        List<ResourceView> resources = resourceApi.get(resourceName);

        if (resources.isEmpty())
            throw new NotFoundException("Resource not found");

        if (resources.size() > 1)
            throw new ResourceException("Return more than one result");

        return resources.get(0);
    }

    public ResourceView getResource(UUID resourceId) {
        return resourceApi.get(resourceId);
    }

    public ResourceView create(Resource resource) {
        return resourceApi.create(resource);
    }

    public void update(UUID uuid, Resource resource) {
        resourceApi.update(uuid, resource);
    }

    public ResourceView upsert(Resource resource) {
        try {
            return resourceApi.create(resource);
        } catch (ConflictException e) {

            if (resource.id != null) {
                update(resource.id, resource);
                return resourceApi.get(resource.id);
            }

            ResourceView resourceView = getResource(resource.name);
            update(resourceView.id, resource);
            return getResource(resource.id);
        }
    }

    public PermissionView getPermission(String permissionName) {
        List<PermissionView> permissions = policyApi.get(permissionName);

        if (permissions.isEmpty())
            throw new NotFoundException("Permission not found");

        if (permissions.size() > 1)
            throw new ResourceException("Permission more than one result");

        return permissions.get(0);
    }

    public PermissionView getPermission(UUID permissionId) {
        return policyApi.get(permissionId);
    }

    public PermissionView upsert(UUID resourceId, Permission permission) {
        try {
            return policyApi.create(resourceId, permission);
        } catch (ConflictException e) {
            PermissionView permissionView = getPermission(permission.name);
            update(permissionView.id, permission);
            return getPermission(permissionView.id);
        }
    }

    public PermissionView create(UUID resourceId, Permission permission) {
        return policyApi.create(resourceId, permission);
    }

    public void update(UUID permissionId, Permission permission) {
        policyApi.update(permissionId, permission);
    }

    public void removePermission(String permissionName) {
        List<PermissionView> permissions = policyApi.get(permissionName);

        if (permissions.isEmpty())
            throw new NotFoundException("Permission not found");

        if (permissions.size() > 1)
            throw new ResourceException("Permission more than one result");

        PermissionView permission = permissions.get(0);

        policyApi.delete(permission.id);
    }

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.service.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RoleService implements RoleRepository {

    private final RoleDataSourceRepository roleDataSourceRepository;

    private final RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    @Override
    public Collection<? extends Role> saveAll(Collection<? extends Role> roleCollection) {
        return roleDataSourceRepository.saveAll(roleCollection)
                .stream()
                .map(RoleModel::new)
                .toList();
    }

    @Override
    public Collection<? extends Role> getRolesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp) {
        return roleDataSourceRepository.getRolesByUserIdAndTimestamp(userId, timestamp);
    }

    @Override
    public String[] getActiveRoleNamesByUserId(Long userId) {
        return roleDataSourceRepository.getActiveRoleNamesByUserId(userId);
    }

    @Override
    public void saveUserRoles(Collection<? extends Role> roleCollection, Long userId) {
        var roleBounds = roleCollection.stream()
                .peek(this::eraseIrrelevantData)
                .collect(Collectors.collectingAndThen(Collectors.toList(), roleDataSourceRepository::getRoleIdByExample))
                .stream()
                .map(roleId -> new RoleBindingModel(userId, roleId, MutableDateTime.now()))
                .peek(roleBindingModel -> roleBindingModel.setCreatedOn(MutableDateTime.now()))
                .toList();
        roleBindingDataSourceRepository.saveAll(roleBounds);
    }

    private void eraseIrrelevantData(Role role) {
        RoleModel roleModel;
        if(role instanceof RoleModel model) {
            roleModel = model;
        } else {
            roleModel = new RoleModel(role);
        }
        roleModel.setCreatedOn(null);
        roleModel.setExpireOn(null);
    }
}

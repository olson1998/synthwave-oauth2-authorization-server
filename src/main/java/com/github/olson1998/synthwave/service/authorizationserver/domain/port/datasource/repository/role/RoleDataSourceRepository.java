package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;

import java.util.Collection;

public interface RoleDataSourceRepository {

    String[] getActiveRoleNamesByUserId(Long userId);

    Collection<Long> getRoleIdByExample(Collection<? extends Role> roleExamples);

    Collection<? extends Role> saveAll(Collection<? extends Role> roleCollection);

}

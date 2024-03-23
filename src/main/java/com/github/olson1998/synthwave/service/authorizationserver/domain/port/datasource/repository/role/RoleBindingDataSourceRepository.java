package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.RoleBinding;

import java.util.Collection;

public interface RoleBindingDataSourceRepository {

    void saveAll(Collection<? extends RoleBinding> roleBoundsCollection);

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.port.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import org.joda.time.MutableDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface RoleRepository {

    Collection<? extends Role> getRolesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp);

    String[] getActiveRoleNamesByUserId(Long userId);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Role> saveAll(Collection<? extends Role> roleCollection);

    @Transactional(rollbackFor = Exception.class)
    void saveUserRoles(Collection<? extends Role> roleCollection, Long userId);

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.port.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteRoleResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserRoleBindingResponse;
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

    @Transactional(rollbackFor = Exception.class)
    DeleteRoleResponse deleteRoles(Collection<Long> idCollection);

    @Transactional(rollbackFor = Exception.class)
    DeleteUserRoleBindingResponse deleteUserRoleBounds(Long userId, Collection<Long> idCollection);

}

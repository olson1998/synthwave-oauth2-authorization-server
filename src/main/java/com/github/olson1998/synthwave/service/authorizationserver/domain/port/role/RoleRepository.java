package com.github.olson1998.synthwave.service.authorizationserver.domain.port.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionPayload;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionRouting;

import java.util.Collection;

import static com.github.olson1998.synthwave.support.masteritem.model.MiTransactions.SAVE;

public interface RoleRepository {

    @TransactionRouting(method = "POST", transaction = SAVE, item = "Role")
    Collection<? extends Role> saveAll(@TransactionPayload Collection<? extends Role> roleCollection);

    String[] getActiveRoleNamesByUserId(Long userId);

    void saveUserRoles(Collection<? extends Role> roleCollection, Long userId);

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.port.masteritem.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionParam;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionPayload;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionRouting;

import java.util.List;

import static com.github.olson1998.synthwave.support.masteritem.model.MiTransactions.*;

public interface ApplicationUserRepository {

    @TransactionRouting(method = "GET", transaction = LIST, item = "ApplicationUser")
    List<ApplicationUser> getUsersByQuery(@TransactionParam(name = "id") String id,
                                          @TransactionParam(name = "name") String name,
                                          @TransactionParam(name = "display_name")  String displayName,
                                          @TransactionParam(name = "active")  Boolean active);

    @TransactionRouting(method = "POST", transaction = SAVE, item = "ApplicationUser")
    ApplicationUser saveUser(@TransactionPayload ApplicationUser applicationUser);

    @TransactionRouting(method = "PUT", transaction = UPDATE, item = "ApplicationUser")
    AffectedRows updateUser(@TransactionPayload ApplicationUser applicationUser);
}

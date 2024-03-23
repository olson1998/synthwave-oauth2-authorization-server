package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionPayload;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionRouting;

import static com.github.olson1998.synthwave.support.masteritem.model.MiTransactions.UPDATE;

public interface UserPasswordRepository {

    void saveNewUserPassword(UserPassword userPassword);

    @TransactionRouting(method = "UPDATE", transaction = UPDATE, item = "Password")
    void updateUserPassword(@TransactionPayload UserPassword userPassword);

}

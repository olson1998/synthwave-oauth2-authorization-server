package com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionPayload;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionRouting;

import java.util.Collection;

public interface AuthorityRepository {

    @TransactionRouting(method = "POST", transaction = "UserAuthorityBind", item = "Authority")
    UserAuthorities executeSaveUserAuthoritiesTransaction(@TransactionPayload UserAuthorities userAuthorities);

    void saveUserAuthorities(UserAuthorities userAuthorities);

}

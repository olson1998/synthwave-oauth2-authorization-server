package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionPayload;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionRouting;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.github.olson1998.synthwave.support.masteritem.model.MiTransactions.SAVE;

public interface ApplicationUserRepository extends UserDetailsService {

    @TransactionRouting(method = "POST", transaction = SAVE, item = "User")
    ApplicationUser saveApplicationUserDetails(@TransactionPayload ApplicationUserDetails applicationUserDetails);
}

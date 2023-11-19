package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsRepository extends UserDetailsService {

    boolean existsUserDetailsForUsername(String username);

    void saveUser(UserSavingRequest userSavingRequest);
}

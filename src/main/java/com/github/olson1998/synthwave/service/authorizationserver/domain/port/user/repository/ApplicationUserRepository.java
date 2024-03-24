package com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import org.joda.time.MutableDateTime;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface ApplicationUserRepository extends UserDetailsService {

    ApplicationUserDetails getApplicationUserDetailsByIdAndTimestamp(Long userId, MutableDateTime timestamp);

    @Transactional(rollbackFor = Exception.class)
    ApplicationUser saveApplicationUserDetails(ApplicationUserDetails applicationUserDetails);


}

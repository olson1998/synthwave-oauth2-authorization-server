package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.UserDetailsData;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;

import java.util.Optional;

public interface ApplicationUserDataSourceRepository {

    ApplicationUser getUserById(Long userId);

    Optional<? extends UserDetailsData> getUserByUsername(String username);

    ApplicationUser save(ApplicationUser applicationUser);

    AffectedRows updateUser(ApplicationUser applicationUser);

    AffectedRows deleteUser(Long userId);
}

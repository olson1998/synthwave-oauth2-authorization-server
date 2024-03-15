package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationUserDataSourceRepository {

    List<ApplicationUser> getByExample(ApplicationUser applicationUser, Integer limit);

    @Transactional
    ApplicationUser save(ApplicationUser applicationUser);

    @Transactional
    AffectedRows updateUser(ApplicationUser applicationUser);

    @Transactional
    AffectedRows deleteUser(Long userId);
}

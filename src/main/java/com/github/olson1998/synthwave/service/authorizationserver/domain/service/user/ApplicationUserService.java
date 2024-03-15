package com.github.olson1998.synthwave.service.authorizationserver.domain.service.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.DefaultApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.masteritem.repository.user.ApplicationUserRepository;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionProcessor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@TransactionProcessor("OA2USR")
@RequiredArgsConstructor
public class ApplicationUserService implements ApplicationUserRepository {

    private final ApplicationUserDataSourceRepository applicationUserDataSourceRepository;

    @Override
    public List<ApplicationUser> getUsersByQuery(String id, String name, String displayName, Boolean active) {
        return applicationUserDataSourceRepository.getByExample(null, null);
    }

    @Override
    public ApplicationUser saveUser(ApplicationUser applicationUser) {
        return applicationUserDataSourceRepository.save(applicationUser);
    }

    @Override
    public AffectedRows updateUser(ApplicationUser applicationUser) {
        return null;
    }

    private ApplicationUser mapToDto(ApplicationUser applicationUser){
        return new DefaultApplicationUser(
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getDisplayName(),
                applicationUser.getCreatedOn(),
                applicationUser.getExpireOn()
        );
    }

}

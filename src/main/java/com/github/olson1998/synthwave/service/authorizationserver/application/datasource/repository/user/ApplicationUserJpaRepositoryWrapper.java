package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.ApplicationUserData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.UserDetailsData;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationUserJpaRepositoryWrapper implements ApplicationUserDataSourceRepository {

    private final ApplicationUserJpaRepository applicationUserJpaRepository;

    @Override
    public ApplicationUser getUserById(Long userId) {
        return applicationUserJpaRepository.selectUserById(userId);
    }

    @Override
    public Optional<? extends UserDetailsData> getUserByUsername(String username) {
        return applicationUserJpaRepository.selectUserDetailsByUsername(username);
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return applicationUserJpaRepository.save(mapToData(applicationUser));
    }

    @Override
    public AffectedRows  updateUser(ApplicationUser applicationUser) {
        var displayName = applicationUser.getDisplayName();
        var id = applicationUser.getId();
        var expireOn = applicationUser.getExpireOn();
        int affectedRows =0;
        if(displayName != null && expireOn != null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserDisplayNameExpireOnById(displayName, expireOn, id);
        } else if(displayName != null && expireOn == null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserDisplayNameById(displayName, id);
        } else if(displayName == null && expireOn != null && id !=null){
            affectedRows = applicationUserJpaRepository.updateApplicationUserExpireOnById(expireOn, id);
        }
        return new AffectedRows(affectedRows);
    }

    @Override
    public AffectedRows deleteUser(Long userId) {
        return new AffectedRows(applicationUserJpaRepository.deleteApplicationUserById(userId));
    }

    private ApplicationUserData mapToData(ApplicationUser applicationUser){
        return new ApplicationUserData(
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getDisplayName(),
                MutableDateTime.now(),
                applicationUser.getExpireOn()
        );
    }
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.service.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetailsSearchQueryResult;
import com.github.olson1998.synthwave.support.hibernate.util.AffectedRows;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionProcessor;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDataSourceRepository.getUserByUsername(username)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    private UserDetails buildUserDetails(ApplicationUserDetailsSearchQueryResult searchQueryResult) {
        var builder = User.builder();
        var expireTimestamp = searchQueryResult.getExpireOn();
        var expired = MutableDateTime.now(expireTimestamp.getZone()).isBefore(searchQueryResult.getExpireOn());
        builder.username(searchQueryResult.getUsername())
                .password(searchQueryResult.getPassword())
                .accountExpired(expired);
        Optional.ofNullable(searchQueryResult.getEnabled()).ifPresent(isEnabled -> builder.disabled(!isEnabled));
        return builder.build();
    }
}

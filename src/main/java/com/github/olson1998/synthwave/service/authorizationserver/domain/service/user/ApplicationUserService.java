package com.github.olson1998.synthwave.service.authorizationserver.domain.service.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.UserAuthoritiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserDetailsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.query.ApplicationUserDetailsSearchQueryResult;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.UserPasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
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

    private final UserPasswordRepository userPasswordRepository;

    private final AuthorityRepository authorityRepository;

    private final ApplicationUserDataSourceRepository applicationUserDataSourceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDataSourceRepository.getUserByUsername(username)
                .map(this::buildUserDetails)
                .orElse(null);
    }

    @Override
    public ApplicationUser saveApplicationUserDetails(ApplicationUserDetails applicationUserDetails) {
        var applicationUser = saveUser(applicationUserDetails.getApplicationUser());
        var userId = applicationUser.getId();
        savePassword(applicationUserDetails.getPassword(), userId);
        List<AuthorityModel> authorityModelList;
        if(applicationUserDetails instanceof ApplicationUserDetailsModel model) {
            authorityModelList = model.getAuthorityModelList();
        } else {
            authorityModelList = applicationUserDetails.getAuthorities()
                    .stream()
                    .map(AuthorityModel::new)
                    .toList();
        }
        saveAuthorities(authorityModelList, userId);
        return new ApplicationUserModel(applicationUser);
    }

    private ApplicationUser saveUser(ApplicationUser applicationUser) {
        ApplicationUserModel applicationUserModel;
        if(applicationUser instanceof ApplicationUserModel model) {
            applicationUserModel = model;
        } else {
            applicationUserModel = new ApplicationUserModel(applicationUser);
        }
        applicationUserModel.setCreatedOn(MutableDateTime.now());
        return applicationUserDataSourceRepository.save(applicationUser);
    }

    private void savePassword(UserPassword userPassword, Long userId) {
        UserPasswordModel userPasswordModel;
        if(userPassword instanceof UserPasswordModel model) {
            userPasswordModel = model;
        } else {
            userPasswordModel = new UserPasswordModel(userPassword);
        }
        userPasswordModel.setUserId(userId);
        userPasswordRepository.saveNewUserPassword(userPassword);
    }

    private void saveAuthorities(List<AuthorityModel> authorityModelList, Long userId) {
        authorityRepository.saveUserAuthorities(new UserAuthoritiesModel(userId, authorityModelList));
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

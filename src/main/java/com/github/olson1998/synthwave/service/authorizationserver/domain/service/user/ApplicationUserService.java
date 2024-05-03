package com.github.olson1998.synthwave.service.authorizationserver.domain.service.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.UserAuthoritiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.role.RoleModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserDetailsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.ApplicationUserModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.SynthwaveUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.user.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.ApplicationUser;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user.UserPassword;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.role.RoleRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.UserPasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.ApplicationUserDetails;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.stereotype.UserDetailsData;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ApplicationUserService implements ApplicationUserRepository {

    private final UserPasswordRepository userPasswordRepository;

    private final RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;

    private final ApplicationUserDataSourceRepository applicationUserDataSourceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDataSourceRepository.getUserByUsername(username)
                .map(this::buildUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User: '%s' has not been found"));
    }

    @Override
    public ApplicationUserDetails getApplicationUserDetailsByIdAndTimestamp(Long userId, MutableDateTime timestamp) {
        timestamp = Optional.ofNullable(timestamp).orElseGet(MutableDateTime::now);
        var user = applicationUserDataSourceRepository.getUserById(userId);
        var roles = roleRepository.getRolesByUserIdAndTimestamp(userId, timestamp);
        var authorities = authorityRepository.getAuthoritiesByUserIdAndTimestamp(userId, timestamp);
        var userDto = new ApplicationUserModel(user);
        var rolesDto = mapToDtoList(roles, RoleModel::new);
        var authoritiesDto = mapToDtoList(authorities, AuthorityModel::new);
        return ApplicationUserDetailsModel.builder()
                .applicationUserModel(userDto)
                .roleModelsList(rolesDto)
                .authorityModelList(authoritiesDto)
                .build();
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
        userPasswordModel.setActive(true);
        userPasswordRepository.saveNewUserPassword(userPassword);
    }

    private void saveAuthorities(List<AuthorityModel> authorityModelList, Long userId) {
        var userAuthorities = new UserAuthoritiesModel(userId, authorityModelList);
        authorityRepository.saveUserAuthorities(userAuthorities);
    }

    private UserDetails buildUserDetails(UserDetailsData userDetailsData) {
        var userId = userDetailsData.getId();
        var builder = SynthwaveUser.synthwaveUserBuilder()
                .userId(userId)
                .companyCode(userDetailsData.getCompanyCode())
                .division(userDetailsData.getDivision());
        var expireTimestamp = userDetailsData.getExpireOn();
        builder.username(userDetailsData.getUsername())
                .password(userDetailsData.getPassword())
                .enabled(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true);
        if(expireTimestamp != null) {
            var expired = MutableDateTime.now(expireTimestamp.getZone()).isBefore(expireTimestamp);
            builder.accountNonExpired(expired);
        } else {
            builder.accountNonExpired(true);
        }
        Optional.ofNullable(userDetailsData.getEnabled()).ifPresentOrElse(builder::enabled, () -> builder.enabled(true));
        builder.authorities(Collections.singletonList(new SimpleGrantedAuthority("user")));
        return builder.build();
    }

    private <T, S> List<S> mapToDtoList(Collection<T> objectCollection, Function<T, S> dtoMapper) {
        return objectCollection.stream()
                .map(dtoMapper)
                .toList();
    }

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AffiliationEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserPropertiesRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.jpa.exception.SelectQueryWithNoResultException;
import com.github.olson1998.synthwave.support.jpa.exception.UnexpectedNumberOfAffectedRowsException;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class UserPropertiesService implements UserPropertiesRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserPasswordDataSourceRepository userPasswordDataSourceRepository;

    private final AffiliationDataSourceRepository affiliationDataSourceRepository;

    @Override
    public boolean existsUserWithUsername(String username) {
        return userDataSourceRepository.existsUserWithGivenUsername(username);
    }

    @Override
    public UserEntity getUserById(TSID id) {
        return userDataSourceRepository.getUserById(id)
                .orElseThrow(()-> new SelectQueryWithNoResultException(UserProperties.class));
    }

    @Override
    public UserEntity saveUserSchema(@NonNull UserSchema userSchema) {
        var userProps = userSchema.getUser();
        var username = userProps.getUsername();
        var passwordProps = userSchema.getPassword();
        var affiliationProperties = userSchema.getAffiliation();
        var userEntity = new UserEntityModel(
                null,
                username,
                false,
                userProps.getExpirePeriod()
        );
        var savedUserProps = userDataSourceRepository.save(userEntity);
        var userId= savedUserProps.getId();
        var encryptedPassword = passwordEncoder.encode(passwordProps.getValue());
        var passwordData = new PasswordEntityModel(
                null,
                userId,
                encryptedPassword,
                true,
                passwordProps.getOptionalExpirePeriod().orElse(null)
        );
        userPasswordDataSourceRepository.save(passwordData);
        var affiliation = new AffiliationEntityModel(
                userId,
                affiliationProperties.getCompanyCode(),
                affiliationProperties.getDivision()
        );
        affiliationDataSourceRepository.save(affiliation);
        log.info(
                "Saved USER: '{}', CODE: '{}' DIVI: '{}'",
                username,
                affiliation.getCompanyCode(),
                affiliation.getDivision()
        );
        return savedUserProps;
    }

    @Override
    public void activateUser(TSID userId) {
        var updatedRows = userDataSourceRepository.setUserEnabledForUserWithId(userId, true);
        if(updatedRows == 1){
            log.debug("Activated user: '{}'", userId.toLong());
        }else {
            throw new UnexpectedNumberOfAffectedRowsException(UserProperties.class, 1, updatedRows);
        }
    }

    @Override
    public void deactivateUser(TSID userId) {
        var updatedRows = userDataSourceRepository.setUserEnabledForUserWithId(userId, false);
        if(updatedRows == 1){
            log.debug("Deactivated user: '{}'", userId.toLong());
        }else {
            throw new UnexpectedNumberOfAffectedRowsException(UserProperties.class, 1, updatedRows);
        }
    }

}

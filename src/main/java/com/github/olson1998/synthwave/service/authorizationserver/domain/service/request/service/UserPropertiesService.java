package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAffiliationEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.exception.UserExistsException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserAffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserPropertiesRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.jpa.exception.SelectQueryWithNoResultException;
import com.github.olson1998.synthwave.support.jpa.exception.UnexpectedNumberOfAffectedRowsException;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class UserPropertiesService implements UserPropertiesRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserPropertiesDataSourceRepository userPropertiesDataSourceRepository;

    private final UserPasswordDataSourceRepository userPasswordDataSourceRepository;

    private final UserAffiliationDataSourceRepository userAffiliationDataSourceRepository;

    @Override
    public boolean existsUserWithUsername(String username) {
        return userPropertiesDataSourceRepository.existsUserWithGivenUsername(username);
    }

    @Override
    public UserEntity getUserById(TSID id) {
        return userPropertiesDataSourceRepository.getUserById(id)
                .orElseThrow(()-> new SelectQueryWithNoResultException(UserProperties.class));
    }

    @Override
    public UserEntity saveUserSchema(UserSchema userSchema) {
        var userProps = userSchema.getUser();
        var username = userProps.getUsername();
        if(userPropertiesDataSourceRepository.existsUserWithGivenUsername(username)){
            throw new UserExistsException(username);
        }
        var passwordProps = userSchema.getPassword();
        var affiliationProperties = userSchema.getAffiliation();
        var userEntity = new UserEntityDTO(
                null,
                username,
                false,
                userProps.getExpirePeriod()
        );
        var savedUserProps = userPropertiesDataSourceRepository.save(userEntity);
        var userId= savedUserProps.getId();
        var encryptedPassword = passwordEncoder.encode(passwordProps.getValue());
        var passwordData = new PasswordEntityDTO(
                null,
                userId,
                encryptedPassword,
                passwordProps.getOptionalExpirePeriod().orElse(null),
                true
        );
        userPasswordDataSourceRepository.save(passwordData);
        var affiliation = new UserAffiliationEntityDTO(
                userId,
                affiliationProperties.getCompanyCode(),
                affiliationProperties.getDivision()
        );
        userAffiliationDataSourceRepository.save(affiliation);
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
        var updatedRows = userPropertiesDataSourceRepository.setUserEnabledForUserWithId(userId, true);
        if(updatedRows == 1){
            log.debug("Activated user: '{}'", userId.toLong());
        }else {
            throw new UnexpectedNumberOfAffectedRowsException(UserProperties.class, 1, updatedRows);
        }
    }

    @Override
    public void deactivateUser(TSID userId) {
        var updatedRows = userPropertiesDataSourceRepository.setUserEnabledForUserWithId(userId, false);
        if(updatedRows == 1){
            log.debug("Deactivated user: '{}'", userId.toLong());
        }else {
            throw new UnexpectedNumberOfAffectedRowsException(UserProperties.class, 1, updatedRows);
        }
    }

}

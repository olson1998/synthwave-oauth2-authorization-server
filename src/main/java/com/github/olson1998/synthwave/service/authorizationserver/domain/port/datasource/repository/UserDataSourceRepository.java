package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import io.hypersistence.tsid.TSID;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public interface UserDataSourceRepository {

    boolean existsUserWithGivenUsername(String username);

    Optional<UserEntity> getUserById(TSID id);

    Optional<UserAffiliation> getUserMetadataByUsername(String username);

    Optional<UserDetails> getUserDetailsByUsername(String username);

    UserEntity save(UserProperties userProperties);

    Collection<UserEntity> saveAll(Collection<UserEntity> userEntityCollection);

    int setUserEnabledForUserWithId(TSID id, boolean isEnabled);

}

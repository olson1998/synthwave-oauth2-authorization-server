package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.DefaultUserDetails;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Optional;

public interface UserPropertiesDataSourceRepository {

    boolean existsUserWithGivenUsername(String username);

    Optional<UserEntity> getUserPropertiesByUsername(String username);

    Optional<UserEntity> getUserById(TSID id);

    Optional<DefaultUserDetails> getSynthWaveUserByUsername(String username);

    UserEntity save(UserEntity userEntity);

    Collection<UserEntity> saveAll(Collection<UserEntity> userEntityCollection);

    int setUserEnabledForUserWithId(TSID id, boolean isEnabled);

}

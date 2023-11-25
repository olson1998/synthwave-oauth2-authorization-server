package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserJpaRepositoryProxy implements UserDataSourceRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsUserWithGivenUsername(String username) {
        return userJpaRepository.selectExistsUserWithUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserById(@NonNull TSID id) {
        return userJpaRepository.selectUserById(id)
                .map(UserEntity.class::cast);
    }

    @Override
    public Optional<UserMetadata> getUserMetadataByUsername(String username) {
        return userJpaRepository.selectUserMetadataByUsername(username)
                .map(UserMetadata.class::cast);
    }

    @Override
    public Optional<UserDetails> getUserDetailsByUsername(@NonNull String username) {
        return userJpaRepository.selectSynthWaveUserByUsername(username)
                .map(UserDetails.class::cast);
    }

    @Override
    public UserEntity save(UserProperties userProperties) {
        var data = new UserData(userProperties);
        return userJpaRepository.save(data);
    }

    @Override
    public Collection<UserEntity> saveAll(@NonNull Collection<UserEntity> userEntityCollection) {
        var data = userEntityCollection.stream()
                .map(UserData::new)
                .toList();
        return userJpaRepository.saveAll(data).stream()
                .map(UserEntity.class::cast)
                .toList();
    }

    @Override
    public int setUserEnabledForUserWithId(TSID id, boolean isEnabled) {
        return userJpaRepository.updateUserEnabledWithGivenUserId(id, isEnabled);
    }

}

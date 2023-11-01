package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.DefaultUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPropertiesJpaRepositoryProxy implements UserPropertiesDataSourceRepository {

    private final UserPropertiesJpaRepository userJpaRepository;

    @Override
    public boolean existsUserWithGivenUsername(String username) {
        return userJpaRepository.selectExistsUserWithUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserPropertiesByUsername(String username) {
        return userJpaRepository.selectUserByUsername(username)
                .map(UserEntity.class::cast);
    }

    @Override
    public Optional<DefaultUserDetails> getSynthWaveUserByUsername(String username) {
        return userJpaRepository.selectSynthWaveUserByUsername(username)
                .map(DefaultUserDetails.class::cast);
    }

    @Override
    public UserEntity save(@NonNull UserEntity userEntity) {
        var data = new UserData(userEntity);
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
}

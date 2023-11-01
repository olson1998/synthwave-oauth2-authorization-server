package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;
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
    public Optional<UserProperties> getUserPropertiesByUsername(String username) {
        return userJpaRepository.selectUserByUsername(username)
                .map(UserProperties.class::cast);
    }

    @Override
    public Optional<DefaultUserDetails> getSynthWaveUserByUsername(String username) {
        return userJpaRepository.selectSynthWaveUserByUsername(username)
                .map(DefaultUserDetails.class::cast);
    }

    @Override
    public UserProperties save(@NonNull UserProperties userProperties) {
        var data = new UserData(userProperties);
        return userJpaRepository.save(data);
    }

    @Override
    public Collection<UserProperties> saveAll(@NonNull Collection<UserProperties> userPropertiesCollection) {
        var data = userPropertiesCollection.stream()
                .map(UserData::new)
                .toList();
        return userJpaRepository.saveAll(data).stream()
                .map(UserProperties.class::cast)
                .toList();
    }
}

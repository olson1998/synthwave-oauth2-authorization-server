package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPropertiesDataSourceRepositoryProxy implements UserPropertiesSourceRepository {

    private final UserPropertiesJpaRepository userJpaRepository;

    @Override
    public Optional<UserProperties> getUserPropertiesByUsername(String username) {
        return userJpaRepository.selectUserByUsername(username)
                .map(UserProperties.class::cast);
    }

    @Override
    public Optional<SynthWaveUserDetails> getSynthWaveUserByUsername(String username) {
        return userJpaRepository.selectSynthWaveUserByUsername(username)
                .map(SynthWaveUserDetails.class::cast);
    }
}

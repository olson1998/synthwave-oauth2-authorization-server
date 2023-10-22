package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.SynthWaveUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SynthWaveUserDataSourceRepositoryProxy implements SynthWaveUserDataSourceRepository {

    private final SynthWaveUserJpaRepository userJpaRepository;

    @Override
    public Optional<SynthWaveUserDetails> getSynthWaveUserByUsername(String username) {
        return userJpaRepository.selectSynthWaveUserByUsername(username)
                .map(SynthWaveUserDetails.class::cast);
    }
}

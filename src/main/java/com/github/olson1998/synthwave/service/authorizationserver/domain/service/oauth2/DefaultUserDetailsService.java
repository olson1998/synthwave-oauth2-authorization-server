package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsRepository {

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserProvisioningRepository userProvisioningRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataSourceRepository.getUserDetailsByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User: '%s' has not been found".formatted(username)));
    }

    @Override
    public boolean existsUserDetailsForUsername(String username) {
        return userDataSourceRepository.existsUserWithGivenUsername(username);
    }

    @Override
    public Optional<UserMetadata> getUserMetadataByUsername(String username) {
        return userDataSourceRepository.getUserMetadataByUsername(username);
    }

    @Override
    public UserMetadata saveUser(SynthWaveUserProperties synthWaveUserProperties) {
        return userProvisioningRepository.provision(synthWaveUserProperties);
    }

}

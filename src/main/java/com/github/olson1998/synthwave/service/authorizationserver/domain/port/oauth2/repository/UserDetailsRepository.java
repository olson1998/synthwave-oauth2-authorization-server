package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserDetailsRepository extends UserDetailsService {

    boolean existsUserDetailsForUsername(String username);

    Optional<UserMetadata> getUserMetadataByUsername(String username);

    UserMetadata saveUser(SynthWaveUserProperties synthWaveUserProperties);
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserDetailsRepository extends UserDetailsService {

    Optional<UserAffiliation> getUserMetadataByUsername(String username);

    UserAffiliation saveUser(SynthWaveUserProperties synthWaveUserProperties);
}

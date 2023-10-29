package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.DefaultUserDetails;

import java.util.Optional;

public interface UserPropertiesSourceRepository {

    Optional<UserProperties> getUserPropertiesByUsername(String username);

    Optional<DefaultUserDetails> getSynthWaveUserByUsername(String username);
}

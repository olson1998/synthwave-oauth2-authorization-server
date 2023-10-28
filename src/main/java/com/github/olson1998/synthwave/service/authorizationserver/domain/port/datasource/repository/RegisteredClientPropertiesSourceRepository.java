package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientProperites;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;

import java.util.Optional;

public interface RegisteredClientPropertiesSourceRepository {

    Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByClientId(String clientId);

    Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByRegisteredClientId(TSID registeredClientId);

    void save(RegisteredClientProperites registeredClientProperites);
}

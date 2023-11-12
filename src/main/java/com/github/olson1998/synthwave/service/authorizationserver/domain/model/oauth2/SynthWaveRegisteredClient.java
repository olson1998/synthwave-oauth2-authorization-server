package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SynthWaveRegisteredClient implements RegisteredClientEntity {

    private final TSID id;

    private final TSID userId;

    private final String clientId;

    public SynthWaveRegisteredClient(TSID userId, String clientId) {
        this.id = null;
        this.userId = userId;
        this.clientId = clientId;
    }
}

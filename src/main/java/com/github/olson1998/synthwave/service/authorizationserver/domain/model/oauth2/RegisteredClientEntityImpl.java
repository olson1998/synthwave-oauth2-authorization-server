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
public class RegisteredClientEntityImpl implements RegisteredClientEntity {

    private final TSID id;

    private final String clientId;

    private final TSID userId;

    public RegisteredClientEntityImpl(TSID userId, String clientId) {
        this.id = null;
        this.clientId = clientId;
        this.userId = userId;
    }
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientProperites;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegisteredClientProps implements RegisteredClientProperites {

    private final TSID id;

    private final TSID userId;

    private final String clientId;
}

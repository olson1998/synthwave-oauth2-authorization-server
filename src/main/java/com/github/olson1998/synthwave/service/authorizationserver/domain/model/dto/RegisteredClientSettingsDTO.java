package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteredClientSettingsDTO implements RegisteredClientSettings {

    private final TSID registeredClientId;

    private final boolean requireProofKey;

    private final boolean requireAuthorizationConsent;
}

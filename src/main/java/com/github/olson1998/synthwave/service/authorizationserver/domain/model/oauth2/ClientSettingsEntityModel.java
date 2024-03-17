package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientSettingsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSettingsEntityModel implements ClientSettingsEntity {

    @JsonProperty("ID")
    private Long registeredClientId;

    @JsonProperty(value = "RPK", required = true)
    private Boolean requireProofKey;

    @JsonProperty(value = "RAC", required = true)
    private Boolean requireAuthorizationConsent;

    @JsonProperty(value = "JWSU")
    private String jwkSetUrl;

    @JsonProperty(value = "JWSA")
    private JwsAlgorithm jwsAlgorithm;

    @JsonProperty(value = "CTMP")
    private MutableDateTime createdOn;

    public ClientSettingsEntityModel(ClientSettingsEntity clientSettings) {
        this.registeredClientId = clientSettings.getRegisteredClientId();
        this.requireProofKey = clientSettings.getRequireProofKey();
        this.requireAuthorizationConsent = clientSettings.getRequireAuthorizationConsent();
        this.jwkSetUrl = clientSettings.getJwkSetUrl();
        this.jwsAlgorithm = clientSettings.getJwsAlgorithm();
        this.createdOn = clientSettings.getCreatedOn();
    }

    public ClientSettingsEntityModel(ClientSettings clientSettings) {
        this.registeredClientId = null;
        this.requireProofKey = clientSettings.isRequireProofKey();
        this.requireAuthorizationConsent = clientSettings.isRequireAuthorizationConsent();
        this.jwkSetUrl = clientSettings.getJwkSetUrl();
        this.jwsAlgorithm = clientSettings.getTokenEndpointAuthenticationSigningAlgorithm();
        this.createdOn = null;
    }

    @Override
    public ClientSettings toSettings() {
        return ClientSettings.builder()
                .requireProofKey(requireProofKey)
                .jwkSetUrl(jwkSetUrl)
                .requireAuthorizationConsent(requireAuthorizationConsent)
                .tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm)
                .build();
    }
}

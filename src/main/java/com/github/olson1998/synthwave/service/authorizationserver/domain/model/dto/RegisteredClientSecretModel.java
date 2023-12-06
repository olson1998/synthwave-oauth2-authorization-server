package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegisteredClientSecretModel extends ClientSecretModel implements RegisteredClientSecret {

    public static final String REGISTERED_CLIENT_SECRET_CLIENT_ID_JSON_FIELD = "registered_client_id";

    private final TSID registeredClientId;

    public RegisteredClientSecretModel(TSID registeredClientId, String value, MutableDateTime expiresDateTime) {
        super(value, expiresDateTime);
        this.registeredClientId = registeredClientId;
    }

    public RegisteredClientSecretModel(TSID registeredClientId, ClientSecret clientSecret) {
        super(clientSecret.getValue(), clientSecret.getExpiresDateTime());
        this.registeredClientId = registeredClientId;
    }
}

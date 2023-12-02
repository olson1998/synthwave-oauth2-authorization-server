package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegisteredClientSecretModel implements RegisteredClientSecret {

    public static final String REGISTERED_CLIENT_SECRET_CLIENT_ID_JSON_FIELD = "registered_client_id";

    public static final String REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD = "value";

    public static final String REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD = "expireAt";

    private final TSID registeredClientId;

    private final String value;

    private final MutableDateTime expiresDateTime;
}

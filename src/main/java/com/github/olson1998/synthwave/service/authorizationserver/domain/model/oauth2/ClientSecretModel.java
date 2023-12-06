package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ClientSecretModel implements ClientSecret {

    public static final String REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD = "value";

    public static final String REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD = "expire_date";

    private final String value;

    private final MutableDateTime expiresDateTime;
}

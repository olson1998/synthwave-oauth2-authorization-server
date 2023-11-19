package com.github.olson1998.synthwave.service.authorizationserver.domain.model.request;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserSavingRequestImpl implements UserSavingRequest {

    public static final String USER_SAVING_REQUEST_USER_JSON_FIELD = "user";

    public static final String USER_SAVING_REQUEST_PASSWORD_JSON_FIELD = "password";

    public static final String USER_SAVING_REQUEST_AFFILIATION_JSON_FIELD = "affiliation";

    private final UserProperties user;

    private final Password password;

    private final Affiliation affiliation;
}

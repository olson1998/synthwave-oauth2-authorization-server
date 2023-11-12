package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import lombok.*;

@Data
@AllArgsConstructor
public class UserSchemaDTO implements UserSchema {

    public static final String USER_SCHEMA_USER_JSON_FIELD = "user";

    public static final String USER_SCHEMA_AFFILIATION_JSON_FIELD = "aff";

    public static final String USER_SCHEMA_PASSWORD_JSON_FIELD = "pass";

    private final UserProperties user;

    private final Password password;

    private final Affiliation affiliation;

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserSchemaDTO implements UserSchema {

    public static final String USER_SCHEMA_USER_JSON_FIELD = "user";

    public static final String USER_SCHEMA_AFFILIATION_JSON_FIELD = "aff";

    public static final String USER_SCHEMA_PASSWORD_JSON_FIELD = "pass";

    private final UserProperties properties;

    private final UserAffiliation affiliation;

    private final Password password;

}

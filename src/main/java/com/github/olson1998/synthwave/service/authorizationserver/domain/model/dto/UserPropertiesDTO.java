package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import lombok.*;
import org.joda.time.Period;

@Data
@AllArgsConstructor
public class UserPropertiesDTO implements UserProperties {

    public static final String USER_NAME_JSON_FIELD = "name";

    public static final String USER_EXP_PERIOD_JSON_FIELD = "exprd";

    private final String username;

    private final Period expirePeriod;
}

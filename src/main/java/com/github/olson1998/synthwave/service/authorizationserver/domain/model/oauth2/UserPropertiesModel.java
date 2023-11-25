package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import lombok.*;
import org.joda.time.Period;

@Data
@AllArgsConstructor
public class UserPropertiesModel implements UserProperties {

    public static final String USER_NAME_JSON_FIELD = "name";

    public static final String USER_EXP_PERIOD_JSON_FIELD = "exprd";

    public static final String USER_ENABLED_JSON_FIELD = "enabled";

    private final String username;

    private final boolean enabled;

    private final Period expirePeriod;
}

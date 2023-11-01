package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.Period;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserEntityDTO implements UserEntity {

    public static final String USER_ID_JSON_FIELD = "id";

    public static final String USER_NAME_JSON_FIELD = "name";

    public static final String USER_ENABLED_JSON_FIELD = "en";

    public static final String USER_EXP_PERIOD_JSON_FIELD = "expin";

    private final TSID id;

    private final String username;

    private final boolean isEnabled;

    private final Period expirePeriod;
}

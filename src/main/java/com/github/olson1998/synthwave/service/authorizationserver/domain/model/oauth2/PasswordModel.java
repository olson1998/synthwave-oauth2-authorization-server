package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.MutableDateTime;

@Data
@AllArgsConstructor
public class PasswordModel implements Password {

    public static final String PASSWORD_VALUE_JSON_FILED = "value";

    public static final String PASSWORD_EXPIRE_DATE_TIME_JSON_FILED = "expire_date_time";

    private final String value;

    private final MutableDateTime expireDateTime;
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import io.hypersistence.tsid.TSID;
import lombok.*;
import org.joda.time.Period;

import java.util.Optional;

@Data
@AllArgsConstructor
public class PasswordDTO implements Password {

    public static final String PASSWORD_VALUE_JSON_FILED = "value";

    public static final String PASSWORD_EXPIRE_PERIOD_JSON_FILED = "expprd";

    private final String value;

    private final Period expirePeriod;

    @Override
    public Optional<Period> getOptionalExpirePeriod() {
        return Optional.ofNullable(expirePeriod);
    }

}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.Period;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PasswordEntityDTO implements PasswordEntity {

    public static final String PASSWORD_ID_JSON_FIELD = "id";

    public static final String PASSWORD_USER_ID_JSON_FIELD = "uid";

    public static final String PASSWORD_VALUE_JSON_FILED = "value";

    public static final String PASSWORD_EXPIRE_PERIOD_JSON_FILED = "expprd";

    public static final String PASSWORD_LATEST_VER_JSON_FIELD = "ltsver";

    private final TSID id;

    private final TSID userId;

    private final String value;

    private final Period expirePeriod;

    private final boolean latestVersion;

    @Override
    public Optional<Period> getOptionalExpirePeriod() {
        return Optional.ofNullable(expirePeriod);
    }
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.Period;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PasswordEntityModel extends PasswordModel implements PasswordEntity {

    public static final String PASSWORD_ID_JSON_FIELD = "id";

    public static final String PASSWORD_LATEST_VER_JSON_FIELD = "ltsver";

    public static final String PASSWORD_USER_ID_JSON_FIELD = "uid";

    private final TSID id;

    private final TSID userId;

    private final Boolean latestVersion;

    public PasswordEntityModel(TSID id, TSID userId, String value, Boolean latestVersion, Period expirePeriod) {
        super(value, userId, latestVersion, expirePeriod);
        this.id = id;
        this.userId = userId;
        this.latestVersion = latestVersion;
    }

}

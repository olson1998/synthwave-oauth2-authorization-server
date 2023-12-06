package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPassword;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserPasswordModel extends PasswordModel implements UserPassword {

    public static final String PASSWORD_USER_ID_JSON_FIELD = "user_id";

    private final TSID userId;

    public UserPasswordModel(TSID userId, String value, MutableDateTime expireDateTime) {
        super(value, expireDateTime);
        this.userId = userId;
    }
}

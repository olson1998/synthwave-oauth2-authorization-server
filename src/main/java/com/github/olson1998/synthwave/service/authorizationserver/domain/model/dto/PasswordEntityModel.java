package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PasswordEntityModel extends UserPasswordModel implements PasswordEntity {

    public static final String PASSWORD_ID_JSON_FIELD = "id";

    private final TSID id;

    public PasswordEntityModel(TSID id, TSID userId, String value, MutableDateTime expireDateTime) {
        super(userId, value, expireDateTime);
        this.id = id;
    }
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.MutableDateTime;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserEntityModel extends UserPropertiesModel implements UserEntity {

    public static final String USER_ID_JSON_FIELD = "id";

    public static final String USER_ENABLED_JSON_FIELD = "en";

    private final TSID id;

    public UserEntityModel(TSID id, String username, Boolean enabled, MutableDateTime expireDateTime) {
        super(username, enabled, expireDateTime);
        this.id = id;
    }
}

package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserAccountLockModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLockEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserAccountLockEntityModel extends UserAccountLockModel implements UserAccountLockEntity {

    public static final String USER_ACCOUNT_LOCK_ID_JSON_FIELD = "lockid";

    private final TSID id;

    public UserAccountLockEntityModel(TSID id, TSID userId) {
        super(userId);
        this.id = id;
    }
}

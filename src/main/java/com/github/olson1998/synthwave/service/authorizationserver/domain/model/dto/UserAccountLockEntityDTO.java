package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLockEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserAccountLockEntityDTO extends UserAccountLockDTO implements UserAccountLockEntity {

    public static final String USER_ACCOUNT_LOCK_ID_JSON_FIELD = "lockid";

    private final TSID id;

    public UserAccountLockEntityDTO(TSID id, TSID userId) {
        super(userId);
        this.id = id;
    }
}

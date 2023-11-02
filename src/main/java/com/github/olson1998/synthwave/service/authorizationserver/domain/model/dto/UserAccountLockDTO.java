package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserAccountLockDTO implements UserAccountLock {

    public static final String USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD = "uid";

    private final TSID userId;
}

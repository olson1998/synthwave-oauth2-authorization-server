package com.github.olson1998.synthwave.service.authorizationserver.domain.model.json;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLock;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserAccountLockDTO implements UserAccountLock {

    public static final String USER_BAN_ID_JSON_FIELD = "banid";

    public static final String USER_BAN_USER_ID_JSON_FIELD = "uid";

    public static final String USER_BAN_TIMESTAMP_FIELD = "bantmp";

    private final TSID id;

    private final TSID userId;

}

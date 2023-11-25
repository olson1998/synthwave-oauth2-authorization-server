package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import io.hypersistence.tsid.TSID;
import lombok.*;

@Data
@AllArgsConstructor
public class UserAccountLockModel implements UserAccountLock {

    public static final String USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD = "uid";

    private final TSID userId;
}

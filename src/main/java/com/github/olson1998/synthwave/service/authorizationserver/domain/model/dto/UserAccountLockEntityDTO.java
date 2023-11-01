package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLockEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import io.hypersistence.tsid.TSID;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserAccountLockEntityDTO implements UserAccountLockEntity {

    public static final String USER_BAN_ID_JSON_FIELD = "banid";

    public static final String USER_BAN_USER_ID_JSON_FIELD = "uid";

    public static final String USER_BAN_TIMESTAMP_FIELD = "bantmp";

    private final TSID id;

    private final TSID userId;

    public UserAccountLockEntityDTO(@NonNull UserAccountLock userAccountLock) {
        this.userId = userAccountLock.getUserId();
        if(userAccountLock instanceof UserAccountLockEntity userAccountLockEntity){
            this.id = userAccountLockEntity.getId();
        }else {
            this.id = null;
        }
    }
}

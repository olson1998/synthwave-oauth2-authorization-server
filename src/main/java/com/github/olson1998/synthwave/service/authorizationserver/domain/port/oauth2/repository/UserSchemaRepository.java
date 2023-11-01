package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;

public interface UserSchemaRepository {

    boolean existsSchema(UserSchema userSchema);

    TSID save(UserSchema userSchema);
}

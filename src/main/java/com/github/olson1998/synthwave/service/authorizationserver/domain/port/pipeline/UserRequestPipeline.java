package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import io.hypersistence.tsid.TSID;

import java.util.Map;

public interface UserRequestPipeline {

    Pipeline<Map<String, String>> runSavingUserPipeline(UserSchema userSchema);

    Pipeline<Void> runSavingActivatedUserPipeline(UserSchema userSchema);

    Pipeline<Void> activateUser(String activationToken);

    Pipeline<Void> deactivateUser(TSID userId);
}

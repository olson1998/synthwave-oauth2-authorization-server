package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegisteredClientEntityModel extends OAuth2ClientModel implements RegisteredClientEntity {

    private final TSID id;

    public RegisteredClientEntityModel(TSID id, TSID userId, String clientId) {
        super(userId, clientId);
        this.id = id;
    }
}

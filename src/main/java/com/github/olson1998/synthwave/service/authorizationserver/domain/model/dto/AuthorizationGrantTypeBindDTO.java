package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Data
@AllArgsConstructor
public class AuthorizationGrantTypeBindDTO implements AuthorizationGrantTypeBinding {

    private final TSID registeredClientId;

    private final AuthorizationGrantType authorizationGrantType;
}

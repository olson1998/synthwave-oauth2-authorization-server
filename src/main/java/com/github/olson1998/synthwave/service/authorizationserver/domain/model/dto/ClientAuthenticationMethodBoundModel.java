package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Data
@AllArgsConstructor
public class ClientAuthenticationMethodBoundModel implements ClientAuthenticationMethodBinding {

    private final TSID registeredClientId;

    private final ClientAuthenticationMethod clientAuthenticationMethod;
}

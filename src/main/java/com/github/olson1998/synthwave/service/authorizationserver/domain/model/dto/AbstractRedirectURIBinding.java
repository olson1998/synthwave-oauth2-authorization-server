package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import lombok.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class AbstractRedirectURIBinding implements RedirectURIBinding {

    private final RedirectURI redirectURI;

    private final Affiliation affiliation;
}

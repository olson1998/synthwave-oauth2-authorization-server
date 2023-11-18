package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRedirectURIBindingEntity extends AbstractRedirectURIBinding implements RedirectURIBinding {

    private final TSID registeredClientId;

    private final Affiliation affiliation;

    protected AbstractRedirectURIBindingEntity(TSID registeredClientId, RedirectURI redirectURI, Affiliation affiliation) {
        super(redirectURI, affiliation);
        this.registeredClientId = registeredClientId;
        this.affiliation = affiliation;
    }
}

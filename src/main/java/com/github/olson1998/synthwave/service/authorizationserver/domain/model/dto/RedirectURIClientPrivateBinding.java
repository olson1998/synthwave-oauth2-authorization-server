package com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class RedirectURIClientPrivateBinding extends AbstractRedirectURIBindingEntity {

    public RedirectURIClientPrivateBinding(TSID registeredClientId, RedirectURI redirectURI) {
        super(registeredClientId, redirectURI, null);
    }

    @Override
    public boolean isClientPrivate() {
        return true;
    }

    @Override
    public boolean isCompanyPrivate() {
        return false;
    }

    @Override
    public boolean isDivisionPrivate() {
        return false;
    }
}
